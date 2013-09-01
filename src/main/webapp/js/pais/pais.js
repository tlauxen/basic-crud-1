$(document).ready(function() {
	ko.applyBindings(new PaisViewModel());
});

function Pais(data) {
    this.id = ko.observable(data.id);
    this.sigla = ko.observable(data.sigla);
    this.nome = ko.observable(data.nome);
}

function PaisViewModel() {
    // Data
    var self = this;

    self.model = ko.observable(new Pais({id: null, sigla: null, nome: null}));
    self.list = ko.observableArray([]);
    self.filter = ko.observable(new Pais({id: null, sigla: null, nome: null}));
    
    self.save = function() {
        Behavior.save(self);
    };
    
    self.remove = function(o) {
    	Behavior.remove(self,o);
    };
    
    self.find = function(o) {
    	Behavior.find(o);
    };
    
    self.cancel = function() {
    	Behavior.cancel();
    };
    
    self.novo = function() {
    	Behavior.novo();
    };
    
    self.edit = function(o) {
    	Behavior.edit(o);
    };
    
    Behavior.init(self);
    
}

