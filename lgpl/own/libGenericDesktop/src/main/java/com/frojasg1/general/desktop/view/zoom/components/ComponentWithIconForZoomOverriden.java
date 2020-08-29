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
package com.frojasg1.general.desktop.view.zoom.components;

import com.frojasg1.general.desktop.view.zoom.imp.ZoomIconImp;
import com.frojasg1.general.number.DoubleReference;
import javax.swing.Icon;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ComponentWithIconForZoomOverriden extends ComponentForZoomOverriden
{
	protected ComponentWithIconForZoomInterface _abstractButtonForZoomComponent = null;

	protected ZoomIconImp _disabledIcon = null;
	protected ZoomIconImp _disabledSelectedIcon = null;
	protected ZoomIconImp _icon = null;
	protected ZoomIconImp _pressedIcon = null;
	protected ZoomIconImp _rollOverIcon = null;
	protected ZoomIconImp _rollOverSelectedIcon = null;
	protected ZoomIconImp _selectedIcon = null;

	public ComponentWithIconForZoomOverriden( ComponentWithIconForZoomInterface component,
									DoubleReference zoomFactor )
	{
		super( component, zoomFactor );
		_abstractButtonForZoomComponent = component;
	}

	protected ZoomIconImp createZoomIcon( Icon icon )
	{
		ZoomIconImp result = null;
		
		if( icon instanceof ZoomIconImp )
		{
			result = (ZoomIconImp) icon;
		}
		else if( icon != null )
		{
			result = new ZoomIconImp( icon, _zoomFactor );
//			result.setAdditionalFactor( 1.33D );
		}
		return( result );
	}

	public Icon getDisabledIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetDisabledIcon();
		if( (_disabledIcon == null) || ( icon != _disabledIcon.getOriginalIcon() ) )
			_disabledIcon = createZoomIcon( icon );

		return( _disabledIcon );
	}

	public Icon getDisabledSelectedIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetDisabledSelectedIcon();
		if(  (_disabledSelectedIcon == null) || ( icon != _disabledSelectedIcon.getOriginalIcon() ) )
			_disabledSelectedIcon = createZoomIcon( icon );

		return( _disabledSelectedIcon );
	}

	public Icon getIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetIcon();
		if(  (_icon == null) || ( icon != _icon.getOriginalIcon() ) )
			_icon = createZoomIcon( icon );

		return( _icon );
	}

	public Icon getPressedIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetPressedIcon();
		if(  (_pressedIcon == null) || ( icon != _pressedIcon.getOriginalIcon() ) )
			_pressedIcon = createZoomIcon( icon );

		return( _pressedIcon );
	}

	public Icon getRolloverIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetRolloverIcon();
		if(  (_rollOverIcon == null) || ( icon != _rollOverIcon.getOriginalIcon() ) )
			_rollOverIcon = createZoomIcon( icon );

		return( _rollOverIcon );
	}

	public Icon getRolloverSelectedIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetRolloverSelectedIcon();
		if(  (_rollOverSelectedIcon == null) || ( icon != _rollOverSelectedIcon.getOriginalIcon() ) )
			_rollOverSelectedIcon = createZoomIcon( icon );

		return( _rollOverSelectedIcon );
	}

	public Icon getSelectedIcon()
	{
		Icon icon = _abstractButtonForZoomComponent.superGetSelectedIcon();
		if(  (_selectedIcon == null) || ( icon != _selectedIcon.getOriginalIcon() ) )
			_selectedIcon = createZoomIcon( icon );

		return( _selectedIcon );
	}
}
