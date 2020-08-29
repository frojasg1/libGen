/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class NewLineSplitter
{
	protected static Pattern _newLinePattern;

	protected static NewLineSplitter _instance = null;

	static
	{
		try
		{
			_newLinePattern = Pattern.compile( "([^\n\r]*)(\r\n|\r|\n|$)" );
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}
	}

	public static NewLineSplitter instance()
	{
		if( _instance == null )
			_instance = new NewLineSplitter();

		return( _instance );
	}
	
	public List<String> split( String text )
	{
		List<String> result = new ArrayList<>();

		Matcher matcher = _newLinePattern.matcher( text );
		while( matcher.find() )
		{
			if( matcher.groupCount() > 0 )
				result.add( matcher.group(1) );
		}
		return( result );
	}
}
