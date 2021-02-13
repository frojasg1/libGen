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
package com.frojasg1.libpdf.utils;

import com.frojasg1.general.desktop.view.ViewFunctions;
import com.frojasg1.libpdf.api.GlyphWrapper;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class PdfUtils
{
	protected static PdfUtils _instance = null;

	public static PdfUtils instance()
	{
		if( _instance == null )
			_instance = new PdfUtils();
		
		return( _instance );
	}


	protected Point center( Rectangle rect )
	{
		return( ViewFunctions.instance().getCenter(rect) );
	}

	protected boolean lookForLine( String line, List<GlyphWrapper> glyphs,
									Rectangle scannerRect, int topYY )
	{
		boolean found = false;
		while( !found && ( scannerRect.y < topYY ) )
		{
			String lineForYY = getLine( glyphs, scannerRect );
			found = line.equals( lineForYY );
			if( !found )
				scannerRect.y++;
		}

		return( found );
	}

	public Stream<GlyphWrapper> getSortedGlyphsStream( List<GlyphWrapper> glyphs,
		Rectangle rect )
	{
		return( glyphs.stream().filter( (gw) -> overlap( rect, gw.getBounds() ) )
			.sorted( (gw1, gw2) -> ( gw1.getBounds().x - gw2.getBounds().x ) ) );
	}

	public List<GlyphWrapper> getSortedGlyphs( List<GlyphWrapper> glyphs,
		Rectangle rect )
	{
		return( getSortedGlyphsStream( glyphs, rect ).collect( Collectors.toList() ) );
	}

	protected boolean overlap( Rectangle rect1, Rectangle rect2 )
	{
		return( ViewFunctions.instance().rectanglesOverlap(rect1, rect2) );
	}

	public String getLine( List<GlyphWrapper> glyphs, Rectangle rect )
	{
		StringBuilder sb = new StringBuilder();

		getSortedGlyphsStream( glyphs, rect )
			.forEach( (gw) -> sb.append( gw.getUnicodeString() ) );

		return( sb.toString() );
	}

}
