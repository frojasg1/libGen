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
package com.frojasg1.general.progress;

/**
 *
 * @author Usuario
 */
public class OperationCancellation
{
/*	public class CancellationException extends Exception
	{
		public CancellationException( String message )
		{
			super( message );
		}
	}
*/	
	protected boolean _hasToCancel=false;

	public OperationCancellation( boolean value )	{ setHasToCancel( value ); }
	
	public boolean getHasToCancel()				{ return( _hasToCancel );	}
	public void setHasToCancel( boolean value )	{ _hasToCancel=value; }
}
