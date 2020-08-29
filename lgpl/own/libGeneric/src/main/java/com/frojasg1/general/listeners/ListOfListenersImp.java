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
package com.frojasg1.general.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ListOfListenersImp< LT > implements ListOfListeners< LT >
{
	protected List<LT> _list;

	public ListOfListenersImp()
	{
		_list = new ArrayList<LT>();
	}

	@Override
	public void add( LT listener )
	{
		_list.add( listener );
	}

	@Override
	public void remove( LT listener )
	{
		ListIterator<LT> it = _list.listIterator();
		while( it.hasNext() )
		{
			LT elem = it.next();
			if( elem.equals( listener ) )
			{
				it.remove();
			}
		}
	}

	@Override
	public void notify( Notifier< LT > notifier )
	{
		for( LT listener: _list )
		{
			notify( listener, notifier );
		}
	}

	protected void notify( LT listener, Notifier< LT > notifier )
	{
		try
		{
			notifier.notify( listener );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void clear()
	{
		_list.clear();
	}
}

