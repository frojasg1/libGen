/* 
 * Copyright (C) 2020 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
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
package com.frojasg1.general.desktop.lookAndFeel;

import com.frojasg1.general.desktop.view.FontFunctions;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ToolTipMetalOceanTheme extends OceanTheme
{
	protected Map< FontUIResource, Map< Integer, FontUIResource > > _map = new HashMap<>();

	protected double _zoomFactor = 1.0D;

    // This does not override getSecondary1 (102,102,102)

    public String getName() {
        return "ToolTipMetalOceanTheme";
    }

	/**
     * Returns true if this is a theme provided by the core platform.
     */
    boolean isSystemTheme() {
//        return (getClass() == ToolTipMetalOceanTheme.class);
        return (false);
    }

    /**
     * Returns the system text font. This returns Dialog, 12pt, plain.
     *
     * @return the system text font
     */
    public FontUIResource getSystemTextFont() {
        return( getZoomedFont( super.getSystemTextFont() ) );
    }

	public void setZoomFactor( double zoomFactor )
	{
		_zoomFactor = zoomFactor;
	}

	protected FontUIResource getFromFontUIResourceMap( FontUIResource original, int newSize )
	{
		FontUIResource result = null;

		Map< Integer, FontUIResource > tmpMap = _map.get( original );

		if( tmpMap != null )
			result = tmpMap.get( newSize );

		return( result );
	}

	protected Map< Integer, FontUIResource > getOrPutFontUIResourceMap( FontUIResource original )
	{
		Map< Integer, FontUIResource > result = _map.get( original );

		if( result == null )
			result = new HashMap<>();

		return( result );
	}

	protected void putInFontUIResourceMapMap( FontUIResource original, FontUIResource result )
	{
		Map< Integer, FontUIResource > tmpMap = getOrPutFontUIResourceMap( original );

		tmpMap.put( result.getSize(), result );
	}

	protected FontUIResource getResizedFont( FontUIResource original, int newSize )
	{
		FontUIResource result = null;
			
		if( original != null )
		{
			result = getFromFontUIResourceMap( original, newSize );

			if( result == null )
			{
				result = (FontUIResource) FontFunctions.instance().getResizedFont(original, newSize);

				putInFontUIResourceMapMap( original, result );
			}
		}

		return( result );
	}

	protected FontUIResource getZoomedFont( FontUIResource original )
	{
		FontUIResource result = null;
		if( original != null )
		{
			int newSize = FontFunctions.instance().getZoomedFontSize( original.getSize(), getZoomFactor() );
			result = getResizedFont( original, newSize );
		}

		return( result );
	}

	protected double getZoomFactor()
	{
		return( _zoomFactor );
	}

	@Override
    public void addCustomEntriesToTable(UIDefaults table) {
		
		super.addCustomEntriesToTable(table);

        Object[] defaults = new Object[] {
            "ScrollBar.squareButtons", true };

        table.putDefaults(defaults);
	}

}
