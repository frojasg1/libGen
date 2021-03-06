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
package com.frojasg1.general.desktop.files;

import com.frojasg1.general.exceptions.GeneralFileException;
import com.frojasg1.general.desktop.files.charset.CharsetManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Usuario
 */
public class TextFileWrapper
{
	protected String _fileName = null;
	protected FileInputStream _fis = null;
	protected InputStreamReader _isr = null;
	protected BufferedReader _br = null;

	protected FileOutputStream _fos = null;
	protected OutputStreamWriter _osw = null;
	protected BufferedWriter _bw = null;

	protected String _charsetName = null;

	protected static final String _saSecureReplaceFileNameSuffix = "._#####";

	protected boolean _fileWasOpenedWithSecureReplaceFileNameSuffix = false;
	
	public TextFileWrapper( String fileName )
	{
		_fileName = fileName;
	}

	public String getCharsetName()			{ return( _charsetName ); }
	public String getFileName()				{ return( _fileName );	}

	public BufferedReader openReadStream() throws GeneralFileException
	{
		if( _br != null )
		{
			throw( new GeneralFileException( "File already opened to read: " + _fileName ) );
		}

		try
		{
			_charsetName = CharsetManager.instance().M_detectCharset( _fileName );

			_fis = null; 
			_isr = null;
			_br = null;

			_fis = new FileInputStream( _fileName );

			if( _charsetName != null )	_isr = new InputStreamReader( _fis, _charsetName );
			else							_isr = new InputStreamReader( _fis );

			_br = new BufferedReader( _isr );
		}
		catch( Throwable th )
		{
			try
			{
				closeReadStream();
			}
			catch( Throwable th1 )
			{
			}

			throw( new GeneralFileException( th.getMessage() ) );
		}

		return( _br );
	}

	public void closeReadStream() throws GeneralFileException
	{
		if( ( _br == null ) && ( _isr == null ) && ( _fis == null ) )
			throw( new GeneralFileException( "The file was not opened for reading." ) );
		
		try
		{
			if( _br != null ) _br.close();
			else if( _isr != null ) _isr.close();
			else if( _fis != null ) _fis.close();
		}
		catch( Throwable th )
		{
			throw( new GeneralFileException( th.getMessage() ) );
		}
		finally
		{
			_br = null;
			_isr = null;
			_fis = null;
		}
	}

	public BufferedWriter openWriteStream( String outputCharsetName, String outputFileName ) throws GeneralFileException
	{
		if( _bw != null )
		{
			throw( new GeneralFileException( "File already opened to write: " + _fileName ) );
		}

		String fileName = outputFileName;
		if( fileName == null )
			fileName = _fileName;
		
		try
		{
			_fileWasOpenedWithSecureReplaceFileNameSuffix = false;

			if( ( outputCharsetName == null ) || outputCharsetName.equals( CharsetManager.instance().getAutodetectString() ) )
				outputCharsetName = _charsetName;

			_fos = null;
			_osw = null;

			_fos = new FileOutputStream( fileName );

			if( outputCharsetName != null )	_osw = new OutputStreamWriter( _fos, outputCharsetName );
			else							_osw = new OutputStreamWriter( _fos );

			_bw = new BufferedWriter( _osw );

			if( outputCharsetName != null )
				_charsetName = outputCharsetName;

			_fileName = fileName;
		}
		catch( Throwable th )
		{
			try
			{
				closeWriteStream();
			}
			catch( Throwable th1 )
			{
			}

			throw( new GeneralFileException( th.getMessage() ) );
		}
		
		return( _bw );
	}

	public void closeWriteStream() throws GeneralFileException
	{
		if( ( _bw == null ) && ( _osw == null ) && ( _fos == null ) )
			throw( new GeneralFileException( "The file was not opened for write." ) );
		
		try
		{
			if( _bw != null ) _bw.close();
			else if( _osw != null ) _osw.close();
			else if( _fos != null ) _fos.close();
		}
		catch( Throwable th )
		{
			throw( new GeneralFileException( th.getMessage() ) );
		}
		finally
		{
			_bw = null;
			_osw = null;
			_fos = null;
		}
	}

	protected BufferedWriter openSecureReplaceStream_internal( String outputCharsetName ) throws GeneralFileException
	{
		// for assigning again the original file name after the execution of openWriteStream.
		String fileName = _fileName;
		
		openWriteStream( outputCharsetName, _fileName + _saSecureReplaceFileNameSuffix );

		_fileName = fileName;
		_fileWasOpenedWithSecureReplaceFileNameSuffix = true;

		return( _bw );
	}

	public BufferedWriter openSecureReplaceStream( String outputCharsetName, String outputFileName ) throws GeneralFileException
	{
		if( _bw != null )
		{
			throw( new GeneralFileException( "File already opened to write: " + _fileName + _saSecureReplaceFileNameSuffix ) );
		}

		if( outputFileName != null )
			_fileName = outputFileName;

		File file = new File( _fileName );
		if( file.exists() )
		{
			openSecureReplaceStream_internal( outputCharsetName );
		}
		else
		{
			openWriteStream( outputCharsetName, null );
		}

		return( _bw );
	}

	public void closeSecureReplaceStream_with_ERROR() throws GeneralFileException
	{
		closeWriteStream();
	}

	public void closeSecureReplaceStream_without_ERROR() throws GeneralFileException
	{
		try
		{
			closeWriteStream();

			if( _fileWasOpenedWithSecureReplaceFileNameSuffix )
			{
				Path source = Paths.get( _fileName + _saSecureReplaceFileNameSuffix );
				Path dest = Paths.get( _fileName );
				Files.move( source, dest, StandardCopyOption.REPLACE_EXISTING );
			}
		}
		catch( Throwable th )
		{
			throw( new GeneralFileException( "Problems repacing file: " + _fileName + ". ERROR: " + th.getMessage() ) );
		}
	}

}
