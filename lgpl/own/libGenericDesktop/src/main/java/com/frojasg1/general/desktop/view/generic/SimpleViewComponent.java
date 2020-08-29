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
package com.frojasg1.general.desktop.view.generic;

import com.frojasg1.general.desktop.generic.DesktopGenericFunctions;
import java.awt.Component;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class SimpleViewComponent implements DesktopViewComponent
{
	protected Component _comp = null;

	public SimpleViewComponent( Component comp )
	{
		_comp = comp;
	}

	@Override
	public Component getComponent()
	{
		return( _comp );
	}

	@Override
	public void setVisible(boolean value)
	{
		_comp.setVisible( value );
	}

	@Override
	public DesktopViewComponent getParentViewComponent()
	{
		return( DesktopGenericFunctions.instance().getViewFacilities().getParentViewComponent(this) );
	}

	@Override
	public void requestFocus()
	{
		_comp.requestFocus();
	}

	@Override
	public boolean isFocusable()
	{
		return( _comp.isFocusable() );
	}

	@Override
	public boolean hasFocus()
	{
		return( _comp.hasFocus() );
	}

	@Override
	public void releaseResources()
	{
		_comp = null;
	}
}
