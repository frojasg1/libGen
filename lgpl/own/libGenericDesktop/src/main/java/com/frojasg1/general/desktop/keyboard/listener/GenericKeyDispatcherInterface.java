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
package com.frojasg1.general.desktop.keyboard.listener;

import com.frojasg1.general.executor.GenericExecutor;
import java.awt.event.KeyEvent;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface GenericKeyDispatcherInterface
{
	public void addKey( int typeOfKey, KeyInterface key, GenericExecutor executor );
	public void changeKey( int typeOfKey, KeyInterface key );
	public void changeExecutor( int typeOfKey, GenericExecutor executor );
	public void removeKey( int typeOfKey );

	public void dispatchKeyEvent( KeyEvent evt );
}
