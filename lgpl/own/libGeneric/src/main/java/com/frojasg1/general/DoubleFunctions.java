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

import com.frojasg1.general.string.StringFunctions;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleFunctions
{
	
	protected static DoubleFunctions _instance;

	protected NumberFormat _formatter = null;

	public static void changeInstance( DoubleFunctions inst )
	{
		_instance = inst;
	}

	public static DoubleFunctions instance()
	{
		if( _instance == null )
			_instance = new DoubleFunctions();
		return( _instance );
	}

	public DoubleFunctions()
	{
		_formatter = createNumberFormat();
	}

	protected NumberFormat createNumberFormat()
	{
		return( new DecimalFormat("#0.000000") );
	}

	public String format( Double value )
	{
		String result = "null";
		if( value != null )
			result = _formatter.format(value);
		return( result );
	}

	public Double min( Double d1, Double d2 )
	{
		Double result = d1;
		if( ( d1 == null ) || ( d2 != null ) && ( d2 < d1 ) )
			result = d2;

		return( result );
	}

	public Double max( Double d1, Double d2 )
	{
		Double result = d1;
		if( ( d1 == null ) || ( d2 != null ) && ( d2 > d1 ) )
			result = d2;

		return( result );
	}

	public int sgn( double dd )
	{
		return( (dd==0) ? 0 : ( (dd>0) ? 1 : -1 ) );
	}

	public double abs( double dd )
	{
		return( (dd==0) ? 0 : ( (dd>0) ? dd : -dd ) );
	}

	public Double parseDouble( String str )
	{
		Double result = null;
		if( str != null )
		{
			String str2 = StringFunctions.instance().replaceSetOfChars(str, ",", "." );
			result = ExecutionFunctions.instance().safeFunctionExecution( () -> Double.parseDouble( str2 ) );
		}
		return( result );
	}
}
