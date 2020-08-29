/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class StraightLineIntegrator
{
/*	protected double _threshold = 0.5D;
	
	public StraightLineIntegrator( double threshold )
	{
		_threshold = threshold;
	}
*/
	public BufferedImage process( BufferedImage image )
	{
		BufferedImage result = new BufferedImage( image.getWidth(), image.getHeight(),
			BufferedImage.TYPE_INT_ARGB );

		int numPoints = image.getWidth() + image.getHeight();
		for( int xx=0; xx<image.getWidth(); xx++ )
			for( int yy=0; yy<image.getHeight(); yy++ )
			{
				int greyScale = (int) (integrate( xx, yy, image ) / numPoints);
                greyScale = 0xff000000 | (greyScale << 16) | (greyScale << 8) | greyScale;

                result.setRGB(xx, yy, greyScale);
			}

		return( result );
	}

	protected long integrate( int xx, int yy, BufferedImage image )
	{
		long result = 0;
		for( int ii=0; ii<image.getWidth(); ii++ )
			result += getGrayScale( image.getRGB(ii, yy) );

		for( int ii=0; ii<image.getHeight(); ii++ )
			result += getGrayScale( image.getRGB(xx, ii) );

		return( result );
	}
	
	public int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        //int gray = (r + g + b) / 3;

        return gray;
    }
}
