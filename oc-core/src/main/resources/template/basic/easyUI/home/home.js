(function() {
	$(document).ready(function() {
		$("#menuTree").tree({
			data : ocFramework.menus,
			onClick : function(node) {
				var centerPanel = $("#layoutBody").layout("panel", "center");
				var pagePath = node.attributes.pagePath;
				centerPanel.panel("refresh", ocFramework.pageContext.requestURI + pagePath);
			}
		});
	});
	
	ocFramework.commonMethod = {
		getConditionUrl : function() {
			var conditionArray = new Array();
	    	$('#conditionDiv div>input').each(function(index, element) {
	    		var conditionObject = new Object();
	    		var elementObject = $(element);
	    		if(elementObject.hasClass("easyui-textbox")) {
	    			conditionObject.value = elementObject.textbox("getValue");
	    			if(conditionObject.value == undefined || conditionObject.value == "") {
	    				return true;
	    			}
	    			conditionObject.name = elementObject.attr("textboxname");
	    		}
	    		conditionObject.op = elementObject.attr("data-operation");
	    		conditionArray.push(conditionObject);
	    	});
	    	return JSON.stringify(conditionArray);
		},
	
		showMessage : function(title, msg, showType) {
            $.messager.show({
                title : title,
                msg : msg,
                showType : showType
            });
		}
	};
})();

