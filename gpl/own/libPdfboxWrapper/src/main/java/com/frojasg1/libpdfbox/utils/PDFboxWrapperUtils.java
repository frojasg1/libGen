/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.libpdfbox.utils;

import com.frojasg1.libpdf.api.GlyphWrapper;
import com.frojasg1.libpdf.api.impl.GlyphImpl;
import com.frojasg1.libpdfbox.impl.MatrixWrapper;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class PDFboxWrapperUtils
{
	protected static PDFboxWrapperUtils _instance;

	public static PDFboxWrapperUtils instance()
	{
		if( _instance == null )
			_instance = new PDFboxWrapperUtils();
		
		return( _instance );
	}

	public void adjustGlyph( BufferedImage pageImage, PDRectangle cropBox,
							float pageFactor, float boundsFactor,
							GlyphWrapper result )
	{
		GlyphImpl gi = (GlyphImpl) result;
		float multiplierFactor = pageFactor / boundsFactor;
//		float multiplierFactor = 1.0f;
//		Rectangle multipliedBounds = applyFactor( bounds, multiplierFactor );
		Rectangle multipliedBounds = applyFactor( result.getBounds(), multiplierFactor );

		gi.setImage( getGlyphImage( pageImage, multipliedBounds ) );
//		gi.setImage( getGlyphImage( pageImage, result.getBounds() ) );
		applyFactorNoCopy( result.getBounds(), 1 / boundsFactor );
	}

	protected int applyFactor( int value, float factor )
	{
		return( (int) Math.round( value * factor ) );
	}

	public Rectangle applyFactor( Rectangle bounds, float factor )
	{
		Rectangle result = null;
		if( bounds != null )
			result = applyFactorNoCopy( new Rectangle( bounds ), factor );

		return( result );
	}

	protected Rectangle applyFactorNoCopy( Rectangle bounds, float factor )
	{
		if( bounds != null )
		{
			bounds.x = applyFactor( bounds.x, factor );
			bounds.y = applyFactor( bounds.y, factor );
			bounds.width = applyFactor( bounds.width, factor );
			bounds.height = applyFactor( bounds.height, factor );
		}

		return( bounds );
	}

	protected BufferedImage getGlyphImage( BufferedImage pageImage, Rectangle bounds )
	{
		return getSubImage( pageImage, bounds ); 
//		return getSubImage( pageImage, new Rectangle( 0, 0, pageImage.getWidth(),
//													pageImage.getHeight() ) ); 
	}

	public BufferedImage getSubImage( BufferedImage image, Rectangle bounds )
	{
		BufferedImage result = new BufferedImage( bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB );
		Graphics2D grp = result.createGraphics();
		grp.drawImage(image, 0, 0, bounds.width, bounds.height,
							bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height,
							null, null);
		grp.dispose();

		return( result );
	}

	public MatrixWrapper createMatrixWrapper( Matrix matrix )
	{
		return( new MatrixWrapper(matrix) );
	}
}
