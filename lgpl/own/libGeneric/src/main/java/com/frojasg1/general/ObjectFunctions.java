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
package com.frojasg1.general;

/**
 *
 * @author fjavier.rojas
 */
public class ObjectFunctions
{
	protected static ObjectFunctions _instance;

	public static void changeInstance( ObjectFunctions inst )
	{
		_instance = inst;
	}

	public static ObjectFunctions instance()
	{
		if( _instance == null )
			_instance = new ObjectFunctions();
		return( _instance );
	}

	public <CC> int compareTo( Comparable<CC> obj, CC obj2 )
	{
		int result = -1;
		if( obj != null )
			result = obj.compareTo(obj2);

		return( result );
	}
}
