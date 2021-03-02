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
package com.frojasg1.general.desktop.image;

import com.frojasg1.desktop.liblens.graphics.Coordinate2D;
import com.frojasg1.general.FileFunctions;
import com.frojasg1.general.desktop.files.DesktopResourceFunctions;
import com.frojasg1.general.number.IntegerFunctions;
import com.frojasg1.general.string.StringFunctions;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

/**
 *
 * @author Usuario
 */
public class ImageUtilFunctions
{
	protected static ImageUtilFunctions _instance = null;

	public static void changeInstance( ImageUtilFunctions inst )
	{
		_instance = inst;
	}

	public static ImageUtilFunctions instance()
	{
		if( _instance == null )
			_instance = new ImageUtilFunctions();

		return( _instance );
	}

	public int getPixelValue( int[] pixels, int index,
								Integer switchColorFrom,
								Integer switchColorTo,
								Integer alphaForPixelsDifferentFromColorFrom )
	{
		return( getPixelValue( pixels[index], switchColorFrom, switchColorTo,
								alphaForPixelsDifferentFromColorFrom) );
	}

	public int invertColor( int inPixelValue )
	{
		int rr = ( inPixelValue & 0xff0000 ) >> 16;
		int gg = ( inPixelValue & 0xff00 ) >> 8;
		int bb = ( inPixelValue & 0xff );
		
		return( ( inPixelValue & 0xff000000 ) + (invertComponent(rr) << 16) +
				(invertComponent(gg) << 8) + invertComponent(bb) );
	}

	protected int invertComponent( int componentValue )
	{
		return( 255 - componentValue );
	}

	public int getPixelValue( int inPixelValue,
								Integer switchColorFrom,
								Integer switchColorTo,
								Integer alphaForPixelsDifferentFromColorFrom )
	{
		int result = inPixelValue;

		int alpha = 0xFF000000;
		if( alphaForPixelsDifferentFromColorFrom != null )
			alpha = ( (alphaForPixelsDifferentFromColorFrom) & 0xFF ) << 24;
			
		if( ( switchColorFrom != null ) && ( result == switchColorFrom ) )
		{
			if( switchColorTo != null ) result = switchColorTo;
			else						result = result & 0xFFFFFF;
		}
		else if( alphaForPixelsDifferentFromColorFrom != null )
		{
			result = result & 0xFFFFFF | alpha;
		}

		return( result );
	}
}
