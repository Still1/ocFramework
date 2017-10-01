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
	})
})();