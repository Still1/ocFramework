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
	
	//公共方法
	ocFramework.commonMethod = {
		//XXX 条件表单必须有一层div
		//XXX 对非文本框是否支持？
		//XXX 考虑创建conditionObject对象的专属类
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
	    		if(elementObject.hasClass('combo-f') && elementObject.combo('options').multiple) {
	    			var values = elementObject.combo('getValues');
	    			if(values.length > 0 && values[0] != '') {
	    				formObject[elementObject.attr('textboxname')] = values;
	    			}
	    		} else if(elementObject.hasClass('textbox-f')) {
	    			var value = elementObject.textbox('getValue');
	    			if(value != '') {
	    				formObject[elementObject.attr('textboxname')] = value;
	    			}
	    		} else {
	    			var value = elementObject.val();
	    			if(value != '') {
	    				formObject[elementObject.attr('name')] = value;
	    			}
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
		},
		
		saveData : function(dataJson, className) {
	    	var csrfHeader = new Object();
	    	csrfHeader[ocFramework.csrfObject.headerName] = ocFramework.csrfObject.token;
	    	$.ajax({
	    		method : 'POST',
	    		url : 'data',
	    		headers : csrfHeader,
	    		traditional : true,
	    		data : {
	    			dataJson : dataJson,
	    			className : className
	    		}
	    	}).done(function() {
	    		ocFramework.commonMethod.showMessage('操作提示', '保存成功', 'fade');
	    	}).fail(function() {
	    		ocFramework.commonMethod.showMessage('操作提示', '保存失败', 'fade');
	    	});
		}
	};
})();

