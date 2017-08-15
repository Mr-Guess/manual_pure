/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	 config.language = 'zh-cn';
	 config.uiColor = '#e2f0f9';
//	 config.toolbarStartupExpanded = false;
	 config.width = '90%';
	 config.height = '500';
	 config.dialog_magnetDistance = '30';
//	 config.toolbarCanCollapse = true;
//	 config.toolbarStartupExpanded = false;
	 config.toolbar = 'Basic';
	 config.toolbar_Basic =
		 [
		     ['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','-','About','-','Styles','Format','Font','FontSize','-',
		      'Maximize','Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat']
		 ];
	 config.resize_enabled = false;
	 config.startupFocus = true;
	 config.tabSpaces = '4';
	 config.undoStackSize = '50';
	 config.removePlugins = 'elementspath';
};
