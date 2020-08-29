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

import com.frojasg1.general.view.ViewComponent;
import com.frojasg1.general.view.ViewFacilities;
import java.awt.Component;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface DesktopViewFacilities extends ViewFacilities
{
	public DesktopViewTextComponent createTextViewComponent( JTextComponent textComp );
	public DesktopViewComponent createViewComponent( Component comp );

	public DesktopViewComponent getParentViewComponent( ViewComponent comp );
}
