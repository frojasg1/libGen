/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.image.processing;

import com.frojasg1.general.desktop.image.processing.utils.ImageUtils;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class Normalizer
{
	public BufferedImage process( BufferedImage image )
	{
		BufferedImage result = new BufferedImage( image.getWidth(), image.getHeight(),
			BufferedImage.TYPE_INT_ARGB );

		int max = getMax( image );
		for( int xx=0; xx<image.getWidth(); xx++ )
			for( int yy=0; yy<image.getHeight(); yy++ )
			{
				int greyScale = getGrayScale( image.getRGB( xx, yy ) );
				if( max > 0 )
					greyScale = ( 255 * greyScale ) / max;
                greyScale = 0xff000000 | (greyScale << 16) | (greyScale << 8) | greyScale;

                result.setRGB(xx, yy, greyScale);
			}

		return( result );
	}

	public int getMax( BufferedImage image )
	{
		int max = 0;
		for( int xx = 0; xx < image.getWidth(); xx++ )
			for( int yy = 0; yy < image.getHeight(); yy++ )
			{
				int grayScale = getGrayScale( image.getRGB(xx, yy) );
				if( max < grayScale )
					max = grayScale;
			}

		return( max );
	}

	public int getGrayScale(int rgb)
	{
		return ImageUtils.instance().getGrayScale(rgb);
    }
}
