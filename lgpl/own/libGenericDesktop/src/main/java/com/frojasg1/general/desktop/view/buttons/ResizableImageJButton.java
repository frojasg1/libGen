/* 
 * Copyright (C) 2021 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package com.frojasg1.general.desktop.view.buttons;

import com.frojasg1.applications.common.components.name.ComponentNameComponents;
import com.frojasg1.general.ExecutionFunctions;
import com.frojasg1.general.desktop.image.ImageFunctions;
import com.frojasg1.general.desktop.view.IconFunctions;
import com.frojasg1.general.desktop.view.ViewFunctions;
import com.frojasg1.general.desktop.view.color.ColorInversor;
import com.frojasg1.general.desktop.view.color.ColorThemeChangeableBaseBuilder;
import com.frojasg1.general.desktop.view.color.impl.ColorThemeChangeableBase;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import com.frojasg1.general.desktop.view.color.ColorThemeInvertible;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ResizableImageJButton extends JButton implements ColorThemeInvertible
{
	protected static final String CLASS_NAME="ResizableImageJButton";
	
	protected double _zoomFactor = 1.0D;
	protected String _jarResourceName = null;
	protected Insets _insets = null;

	protected BufferedImage _image = null;

	protected ColorThemeChangeableBase _colorThemeStatus;

	public ResizableImageJButton( String jarResourceName, Insets insets )
	{
		_jarResourceName = jarResourceName;
		_insets = insets;
		setImage_final();

		_colorThemeStatus = createColorThemeChangeableBase();
//		addActionListener(this);
	}

	protected ColorThemeChangeableBase createColorThemeChangeableBase()
	{
		return( ColorThemeChangeableBaseBuilder.instance().createColorThemeChangeableBase() );
	}

	public final void setImage_final( String jarResourceNameForImage )
	{
		_jarResourceName = jarResourceNameForImage;

		_image = ExecutionFunctions.instance().safeFunctionExecution( () -> ImageFunctions.instance().loadImageFromJar(_jarResourceName) );
		if( _image != null )
			setImage_final( _image );
//		_image = ViewFunctions.instance().addImageToButtonAccurate( this, _jarResourceName, _insets );
	}

	protected final void setImage_final()
	{
		if( _image != null )
			setImage_final( _image );
		else
			setImage_final( _jarResourceName );
	}

	public final void setImage_final( BufferedImage image )
	{
		ViewFunctions.instance().addImageToButtonAccurate( this, image, _insets );
	}

	public void resizeImage()
	{
		setImage_final();
	}

	@Override
	public void setBounds( int xx, int yy, int width, int height )
	{
		super.setBounds( xx, yy, width, height );
		resizeImage();
	}

	@Override
	public void setSize( int width, int height )
	{
		super.setSize( width, height );
		resizeImage();
	}
/*
	@Override
	public void actionPerformed(ActionEvent e)
	{
	}
*/
	public static String getImageResourceFromName( String name )
	{
		String result = null;
/*		if( ( name != null ) &&
			( name.length() > ( CLASS_NAME.length() + 1 ) ) &&
			( name.substring( 0, CLASS_NAME.length() + 1 ).equals( CLASS_NAME + "=" ) )
			)
		{
			result = name.substring( CLASS_NAME.length() + 1 );
		}
*/
		ComponentNameComponents cnc = new ComponentNameComponents( name );
		result = cnc.getComponent( ComponentNameComponents.ICON_COMPONENT );

		return( result );
	}

	public static ResizableImageJButton createResizableImageJButton( JButton jbutton )
	{
		Insets insets = new Insets( 1, 1, 1, 1 );
		ResizableImageJButton result = null;
		String imageResource = getImageResourceFromName( jbutton.getName() );

		if( imageResource != null )
		{
			result = new ResizableImageJButton( imageResource, insets );

//			ActionListener[] al = jbutton.getActionListeners();
//			for( int ii=0; ii<al.length; ii++ )
//				result.addActionListener( al[ii] );
		}

		return( result );
	}

	@Override
	public void setIcon( Icon icon )
	{
//		writeImage( IconFunctions.instance().toImage( icon ), "_setIcon" );

		super.setIcon( icon );
	}

	protected void writeImage( BufferedImage image, String suffix )
	{
		String name = ( getName() == null ) ? "default" : getName().replaceAll( "[:\\\\./]", "_" );
		String name2 = name + suffix;
		if( image != null )
			ExecutionFunctions.instance().safeMethodExecution( () -> ImageIO.write( image, "png", new File( "J:\\" + name2 + ".png" ) ) );
	}

	protected void invertImageColors()
	{
//		writeImage( _image, "_preInvert" );
		_image = ImageFunctions.instance().invertImage(_image);
//		writeImage( _image, "_postInvert" );
		setImage_final();
	}

	@Override
	public void invertColors( ColorInversor colorInversor )
	{
		invertImageColors();
		colorInversor.invertColor( getBackground(), this::setBackground );
		colorInversor.invertColor( getForeground(), this::setForeground );
	}
}
