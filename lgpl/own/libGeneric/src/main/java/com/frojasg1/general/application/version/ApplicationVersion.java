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
package com.frojasg1.general.application.version;

import com.frojasg1.general.FileFunctions;

/**
 *
 * @author fjavier.rojas
 */
public class ApplicationVersion
{
	protected static final String DEFAULT_DOWNLOAD_FILE_FILENAME = "_newVersion/downloadFile.txt";

	protected String _downloadFileName = DEFAULT_DOWNLOAD_FILE_FILENAME;

	protected static ApplicationVersion _instance;

	protected String _downloadFile = null;

	public static void changeInstance( ApplicationVersion inst )
	{
		_instance = inst;
	}

	public static ApplicationVersion instance()
	{
		if( _instance == null )
			_instance = new ApplicationVersion();
		return( _instance );
	}

	protected String getContentsOfFile( String fileName )
	{
		return( FileFunctions.instance().loadTextFileContent(fileName) );
	}

	public void setDownloadFileFileName( String fileName )
	{
		_downloadFileName = fileName;
	}

	public String getDownloadFileFileName( )
	{
		return( _downloadFileName );
	}

	protected String calculateDownloadFile()
	{
		String result = getContentsOfFile( getDownloadFileFileName() );
		if( result != null )
		{
			String [] arr = result.split( "\\s" );
			if( arr.length > 0 )
			{
				result = arr[0];
			}
		}

		return( result );
	}

	public String getDownloadFile()
	{
		if( _downloadFile == null )
			_downloadFile = calculateDownloadFile();

		return( _downloadFile );
	}
}
