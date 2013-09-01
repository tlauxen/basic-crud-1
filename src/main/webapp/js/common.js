var Behavior = {
		
	init: function(viewModel) {
	    // Client-side routes    
	    Sammy(function() {
	        this.get('#:id', function() {
	    		Behavior.load(this.params.id, viewModel);
	        });

	    }).run();
	},
		
	novo: function() {
		var path = Behavior.substringPath();
		path += "/cadastro";
		window.location.href = path;
	},
	
	edit: function(o) {
		var path = Behavior.substringPath();
		path += "/cadastro/#"+o.id;
		window.location.href = path;
	},
	
	load: function(id, viewModel) {
		var path = Behavior.substringPath();
		Behavior.blockForm();
		path += "/ajax/findById";
		$.ajax(path, {
        	dataType: 'json',
            contentType: "application/json",
            data: {id: id},
            type: "get",
            success: function(result) {
            	viewModel.model(result);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error("Ocorreu um problema ao carregar o registro: " + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });

	},
	
	cancel: function() {
		var path = Behavior.substringPath();
		path += "/listagem";
		window.location.href = path;
	},
	
	save: function(viewModel) {

		var path = Behavior.substringPath();
		Behavior.blockForm();
		path += "/ajax/save";
		var json = ko.toJSON(viewModel.model);
		$.ajax(path, {
        	dataType: 'json',
            contentType: "application/json",
            data: json,
            type: "post",
            success: function(result) {
            	viewModel.model(result);
            	toastr.success("Registro salvo com sucesso!");
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error("Ocorreu um problema ao salvar o registro: " + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	remove: function(viewModel, o) {

		var path = Behavior.substringPath();
		Behavior.blockForm();
		path += "/ajax/remove";
		var json = ko.toJSON(o);
		$.ajax(path, {
        	contentType: "application/json",
            data: json,
            type: "post",
            success: function() {
            	viewModel.list([]);
            	toastr.success("Registro removido com sucesso!");
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error("Ocorreu um problema ao remover o registro: " + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	find: function(viewModel) {

		var path = Behavior.substringPath();
		Behavior.blockForm();
		path += "/ajax/find";
		var json = ko.toJSON(viewModel.filter);
		$.ajax(path, {
        	dataType: 'json',
            contentType: "application/json",
            data: json,
            type: "post",
            success: function(result) {
            	viewModel.list(result);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error("Ocorreu um problema ao executar a consulta: " + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	substringPath: function() {
		var path = window.location.href;
		if (path.lastIndexOf("/cadastro") > -1) {
			path = path.substring(0, path.lastIndexOf("/cadastro"));
		} else if (path.lastIndexOf("/listagem") > -1) {
			path = path.substring(0, path.lastIndexOf("/listagem"));
		} else {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		return path;
	},
	
	blockForm: function() {
		$('form').block({ message: '<img alt="Processando..." src="/img/ajax-loader.gif"></img>',
			css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000',
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px',
	            opacity: .5
        	},
        	overlayCSS: { opacity: 0 }
		});
	},
	
	unBlockForm: function() {
		$('form').unblock();
	}
	
};
