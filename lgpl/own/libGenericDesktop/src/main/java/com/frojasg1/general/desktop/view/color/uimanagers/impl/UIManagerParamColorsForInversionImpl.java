/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.color.uimanagers.impl;

import com.frojasg1.general.desktop.view.color.uimanagers.UIManagerParamColorsForInversionBase;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class UIManagerParamColorsForInversionImpl extends UIManagerParamColorsForInversionBase
{
	protected static UIManagerParamColorsForInversionImpl _instance;

	public static UIManagerParamColorsForInversionImpl instance()
	{
		if( _instance == null )
			_instance = createInstance();

		return( _instance );
	}

	private synchronized static UIManagerParamColorsForInversionImpl createInstance()
	{
		if( _instance == null )
			_instance = new UIManagerParamColorsForInversionImpl();

		return( _instance );
	}

	private UIManagerParamColorsForInversionImpl()
	{
		init();
	}

	protected void init()
	{
		super.init();

		addKey( "SplitPane.dividerFocusColor" );
		addKey( "SplitPaneDivider.draggingColor" );
//		addKey( "ProgressBar.background" );
//		addKey( "ProgressBar.foreground" );
		addKey( "ProgressBar.selectionBackground" );
		addKey( "ProgressBar.selectionForeground" );
		addKey( "TabbedPane.selected" );
		addKey( "ToggleButton.select" );
	}
}
