var Behavior = {
		
	//Initial things
	init: function(viewModel) {
	    // Client-side routes
	    Sammy(function() {
	        this.get('#:id', function() {
	    		Behavior.load(this.params.id, viewModel);
	        });

	    }).run();
	},
	
	//NAVIGATION things
	
	//GOTO Create entity
	novo: function() {
		var path = Behavior.substringPath();
		path += Constants.CREATE_EDIT_PATH;
		window.location.href = path;
	},
	
	//GOTO edit entity
	edit: function(o) {
		var path = Behavior.substringPath();
		path += Constants.CREATE_EDIT_PATH+"/#"+o.id;
		window.location.href = path;
	},

	//GOTO Go back to the list
	cancel: function() {
		var path = Behavior.substringPath();
		path += Constants.LIST_PATH;
		window.location.href = path;
	},
	
	//AJAX things
	
	//AJAX load an entity by the ID
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
            	viewModel.loadModel(result);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error(Constants.ERROR_WHEN_LOADING_ENTITY + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });

	},
	
	//AJAX save an entity
	save: function(viewModel) {

		var path = Behavior.substringPath();
		Behavior.blockForm();
		path += "/ajax/save";
		var json = viewModel.toJSON();
		$.ajax(path, {
        	dataType: 'json',
            contentType: "application/json",
            data: json,
            type: "post",
            success: function(result) {
            	viewModel.loadModel(result);
            	toastr.success(Constants.SUCCESS_SAVING_ENTITY);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error(Constants.ERROR_WHEN_SAVING_ENTITY + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	//AJAX remove an entity
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
            	toastr.success(Constants.SUCCESS_REMOVING_ENTITY);
            	Behavior.find(viewModel);
            },
            error: function( jqXHR, textStatus, errorThrown) {
            	toastr.error(Constants.SUCCESS_REMOVING_ENTITY + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	//AJAX find by filter and reload list
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
            	toastr.error(Constants.ERROR_WHEN_QUERYING + textStatus + " - " + errorThrown);
            },
            complete: function() {
            	Behavior.unBlockForm();
            }
        });
	},
	
	//COMMON functions
	
	//Retrive initial path from url
	substringPath: function() {
		var path = window.location.href;
		if (path.lastIndexOf(Constants.CREATE_EDIT_PATH) > -1) {
			path = path.substring(0, path.lastIndexOf(Constants.CREATE_EDIT_PATH));
		} else if (path.lastIndexOf(Constants.LIST_PATH) > -1) {
			path = path.substring(0, path.lastIndexOf(Constants.LIST_PATH));
		} else {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		return path;
	},
	
	//Retrive initial path from url
	getContextPath: function() {
		var path = Behavior.substringPath();
		path = path.substring(0, path.lastIndexOf("/"));
		return path;
	},
	
	//Block form
	blockForm: function() {
		$('form').block({ message: '<img alt="'+Constants.WORKING+'..." src="'+Behavior.getContextPath()+Constants.LOADER_IMG+'"></img>',
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
	
	//Unblock form
	unBlockForm: function() {
		$('form').unblock();
	}
	
};
