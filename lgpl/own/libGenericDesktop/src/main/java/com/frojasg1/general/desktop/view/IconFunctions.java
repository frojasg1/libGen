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
package com.frojasg1.general.desktop.view;

import com.frojasg1.general.desktop.view.zoom.ZoomIcon;
import com.frojasg1.general.desktop.view.zoom.imp.ZoomIconImp;
import javax.swing.Icon;
import com.frojasg1.general.number.DoubleReference;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class IconFunctions {
	
	protected static IconFunctions _instance;

	public static void changeInstance( IconFunctions inst )
	{
		_instance = inst;
	}

	public static IconFunctions instance()
	{
		if( _instance == null )
			_instance = new IconFunctions();
		return( _instance );
	}

	public ZoomIcon createZoomIcon( Icon icon )
	{
		ZoomIcon result = new ZoomIconImp( icon );

		return( result );
	}
}
