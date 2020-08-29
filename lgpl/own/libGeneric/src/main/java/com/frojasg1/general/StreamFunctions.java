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
package com.frojasg1.general;

import com.frojasg1.general.desktop.files.charset.CharsetManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class StreamFunctions
{
	protected static StreamFunctions _instance;

	public static void changeInstance( StreamFunctions inst )
	{
		_instance = inst;
	}

	public static StreamFunctions instance()
	{
		if( _instance == null )
			_instance = new StreamFunctions();
		return( _instance );
	}

	public InputStream getInputStream( String str, Charset charset )
	{
		if( charset == null )
			charset = StandardCharsets.UTF_8;

		InputStream result = null;
		try
		{
			result = new ByteArrayInputStream( str.getBytes( charset.name() ));
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		
		return( result );
	}

	public String readTextInputStream( InputStream is )
	{
		String result = null;

		try
		{
			byte[] bytes = new byte[ is.available() ];
			is.read( bytes );

			String charsetName = CharsetManager.instance().M_detectCharset(is);

			if( charsetName != null )
				result = new String( bytes, charsetName );
			else
				result = new String( bytes );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return( result );
	}
}
