$(document).ready(function() {
	
	//Apply knockout bindings
	ko.applyBindings(new PaisViewModel());
});

//Knockout view model
function PaisViewModel() {

	var self = this;

	//DATA
    //Model: entity being created or edited 
    self.model = ko.observable(new Pais({id: null, sigla: null, nome: null}));
    
    //List of entitys
    self.list = ko.observableArray([]);
    //Filter the list
    self.filter = ko.observable(new Pais({id: null, sigla: null, nome: null}));
    
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
    	self.model(new Pais(o));
    };
    
    self.toJSON = function() {
		var pais = new Pais(self.model());
		var json = ko.toJSON(pais);
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

    //INITIALIZATION
    
    Behavior.init(self);
    
}

