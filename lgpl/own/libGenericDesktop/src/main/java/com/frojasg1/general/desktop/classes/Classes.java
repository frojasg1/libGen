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
package com.frojasg1.general.desktop.classes;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class Classes {

	public static Class<?> getClassForName( String className )
	{
		Class<?> result = null;

		try
		{
			result = Class.forName( className );
		}
		catch( Exception ex )
		{}

		return( result );
	}

	public static Class<?> getFilePaneClass()
	{
		return( getClassForName( "sun.swing.FilePane" ) );	// JRE 7, 8, ...
	}

	public static Class<?> getAppContextClass()
	{
		return( getClassForName( "sun.awt.AppContext" ) );	// JRE 7, 8, ...
	}
}
