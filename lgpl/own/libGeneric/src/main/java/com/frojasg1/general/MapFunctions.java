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

import java.util.Map;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class MapFunctions
{
	protected static MapFunctions _instance;

	public static void changeInstance( MapFunctions inst )
	{
		_instance = inst;
	}

	public static MapFunctions instance()
	{
		if( _instance == null )
			_instance = new MapFunctions();
		return( _instance );
	}
	
	public <KK,VV> VV get( Map<KK,VV> map, KK key )
	{
		VV result = null;

		if( map != null )
		{
			result = map.get(key);
		}

		return( result );
	}
}
