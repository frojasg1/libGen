/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.icons;

import com.frojasg1.general.desktop.view.zoom.imp.ZoomIconImp;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomIconBuilder
{
	protected static ZoomIconBuilder _instance;

	public static void changeInstance( ZoomIconBuilder inst )
	{
		_instance = inst;
	}

	public static ZoomIconBuilder instance()
	{
		if( _instance == null )
			_instance = new ZoomIconBuilder();
		return( _instance );
	}

	protected double calculateFactor( double size, double target )
	{
		double result = 1;

		if( size != 0 )
			result = target / size;

		return( result );
	}

	public Double calculateAdditionalFactor( Icon icon, Dimension dimen )
	{
		Double result = null;

		if( ( icon != null ) && ( dimen != null ) )
		{
			double xFactor = calculateFactor( icon.getIconWidth(), dimen.width );
			double yFactor = calculateFactor( icon.getIconHeight(), dimen.height );

			result = ( xFactor > yFactor ) ? yFactor : xFactor;
		}

		return( result );
	}

	public Icon createOriginalIcon( BufferedImage image )
	{
		Icon result = null;

		try
		{
			result = new ImageIcon( image );
		}
		catch( Exception ex )
		{}

		return( result );
	}

	public ZoomIconImp createZoomIcon( BufferedImage image, Dimension dimen )
	{
		return( createZoomIcon( createOriginalIcon(image), dimen ) );
	}

	public ZoomIconImp createZoomIcon( BufferedImage image, double factor )
	{
		return( createZoomIcon( createOriginalIcon(image), factor ) );
	}

	public ZoomIconImp createZoomIcon( Icon originalIcon, double factor )
	{
		ZoomIconImp result = new ZoomIconImp( originalIcon );
		result.setAdditionalFactor(factor);

		return( result );
	}

	public ZoomIconImp createZoomIcon( Icon originalIcon, Dimension dimen )
	{
		ZoomIconImp result = null;
		Double factor = calculateAdditionalFactor( originalIcon, dimen );
		if( factor != null )
		{
			result = createZoomIcon( originalIcon, factor );
		}

		return( result );
	}
}
