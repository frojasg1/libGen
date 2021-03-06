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
package com.frojasg1.general.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ListOfListenersImp< LT > extends ListOfListenersBase<LT>
									implements ListOfListenersGenListener< LT >
{
	public ListOfListenersImp()
	{
		super();
	}

	@Override
	public void notifyListeners( Notifier< LT > notifier )
	{
		for( LT listener: getList() )
			notifyListener( listener, notifier );
	}

	protected void notifyListener( LT listener, Notifier< LT > notifier )
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
}

