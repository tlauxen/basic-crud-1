$(document).ready(function() {
	
	//Apply knockout bindings
	ko.applyBindings(new EstadoViewModel());
});

//Knockout view model
function EstadoViewModel() {

	var self = this;

	//DATA
    this.model = ko.observable(new Estado({id: null, pais: new Pais({id: null, sigla: null, nome: null}), sigla: null, nome: null}));

    this.pais = ko.observable(new Pais({id: null, sigla: null, nome: null}));
    
    //List of entitys
    self.list = ko.observableArray([]);
    //Filter the list
    self.filter = ko.observable(new Estado({id: null, pais: new Pais({id: null, sigla: null, nome: null}), sigla: null, nome: null}));
    //Pais list (combo)
    self.paises = ko.observableArray([]);
    
    
    //AJAX
    
    self.save = function() {
        Behavior.save(self);
    };
    
    self.remove = function(o) {
    	Behavior.remove(self,o);
    };
    
    self.find = function(o) {
    	Behavior.find(o);
    };
    
    self.loadModel = function(o) {
    	var estado = new Estado(o);
    	self.model(estado);
    	$.each(self.paises(), 
    		function() {
    			if (this.id == estado.pais.id) {
    				self.pais(this);
    				return false;
    			}
    		}
    	);
    };
    
    self.toJSON = function() {
		var estado = new Estado(self.model());
		estado.pais = new Pais(self.pais());
		var json = ko.toJSON(estado);
		return json;
    };

    //BEHAVIOR
    
    self.cancel = function() {
    	Behavior.cancel();
    };
    
    self.novo = function() {
    	Behavior.novo();
    };
    
    self.edit = function(o) {
    	Behavior.edit(o);
    };
    
	//AJAX find by filter and reload list
    self.findPaises = function(viewModel) {

		var path = Behavior.getContextPath();
		Behavior.blockForm();
		path += "/pais/ajax/find";
		var json = ko.toJSON(new Pais({id: null, sigla: null, nome: null}));
		$.ajax(path, {
        	dataType: 'json',
            contentType: "application/json",
            data: json,
            type: "post",
            success: function(result) {
            	viewModel.paises(result);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error(Constants.ERROR_WHEN_QUERYING + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
                Behavior.init(self);
            }
        });
	},
	
    //INITIALIZATION
    
    self.findPaises(self);
    
    
}

