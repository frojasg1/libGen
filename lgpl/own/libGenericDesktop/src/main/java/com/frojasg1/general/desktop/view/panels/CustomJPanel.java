/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.panels;

import com.frojasg1.general.desktop.view.FrameworkComponentFunctions;
import com.frojasg1.general.desktop.view.color.ColorInversor;
import com.frojasg1.general.desktop.view.color.impl.ColorThemeChangeableForCustomComponent;
import java.awt.Color;
import javax.swing.JPanel;
import com.frojasg1.general.desktop.view.color.ColorThemeInvertible;
import com.frojasg1.general.view.ReleaseResourcesable;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class CustomJPanel extends JPanel
									implements ColorThemeInvertible,
												ReleaseResourcesable
{
	boolean _doNotInvertColors = false;

	public CustomJPanel()
	{
		this(false);
	}

	public CustomJPanel(boolean doNotInvertColors )
	{
		super();
		_doNotInvertColors = doNotInvertColors;
	}

	protected void init()
	{
	}

	protected void invertColorsChild(ColorInversor colorInversor)
	{
		// If children want to change the behaviour, they have to override
	}

	protected void invertSinglePanelColors(ColorInversor colorInversor)
	{
		colorInversor.invertSingleColorsGen(this);
	}

	@Override
	public void invertColors( ColorInversor colorInversor)
	{
		if( ! _doNotInvertColors )
		{
			invertSinglePanelColors(colorInversor);
			invertColorsChild(colorInversor);
		}
	}

	@Override
	public void setBackground( Color bc )
	{
		super.setBackground(bc);
	}

	protected ColorInversor getColorInversor()
	{
		return( FrameworkComponentFunctions.instance().getColorInversor(this) );
	}

	protected boolean isDarkMode()
	{
		return( FrameworkComponentFunctions.instance().isDarkMode(this) );
	}

	protected boolean wasLatestModeDark()
	{
		return( FrameworkComponentFunctions.instance().wasLatestModeDark(this) );
	}

	@Override
	public void releaseResources()
	{
		// intentionally left blank
	}
}
