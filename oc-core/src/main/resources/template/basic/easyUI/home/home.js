(function() {
	$(document).ready(function() {
		$('#menuTree').tree({
			data : ocFramework.menus,
			onClick : function(node) {
				var centerPanel = $('#layoutBody').layout('panel', 'center');
				var pagePath = node.attributes.pagePath;
				centerPanel.panel('refresh', ocFramework.pageContext.requestURI + pagePath);
			}
		});
	});
	
	ocFramework.commonMethod = {
		//XXX 条件表单必须有一层div
		getConditionUrl : function(conditionFormId) {
			var conditionArray = new Array();
	    	$(conditionFormId + '>div>input').each(function(index, element) {
	    		var conditionObject = new Object();
	    		var elementObject = $(element);
	    		if(elementObject.hasClass('textbox-f')) {
	    			conditionObject.value = elementObject.textbox('getValue');
	    			if(conditionObject.value == undefined || conditionObject.value == '') {
	    				return true;
	    			}
	    			conditionObject.name = elementObject.attr('textboxname');
	    		}
	    		conditionObject.op = elementObject.attr('data-operation');
	    		conditionArray.push(conditionObject);
	    	});
	    	return JSON.stringify(conditionArray);
		},
		
		getFormDataJson : function(formId) {
			var formObject = new Object();
			$(formId + '>div>:input').each(function(index, element) {
				var elementObject = $(element);
	    		if(elementObject.hasClass('textbox-f')) {
	    			formObject[elementObject.attr('textboxname')] = elementObject.textbox('getValue');
	    		} else {
	    			formObject[elementObject.attr("name")] = elementObject.val();
	    		}
	    	});
	    	return JSON.stringify(formObject);
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

