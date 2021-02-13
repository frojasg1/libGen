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
public class ClassFunctions
{
	protected static ClassFunctions _instance;

	public static void changeInstance( ClassFunctions inst )
	{
		_instance = inst;
	}

	public static ClassFunctions instance()
	{
		if( _instance == null )
			_instance = new ClassFunctions();
		return( _instance );
	}

	public <CC> CC cast( Object obj, Class<CC> clazz )
	{
		CC result = null;
		if( clazz.isInstance(obj) )
			result = (CC) obj;

		return( result );
	}
}
