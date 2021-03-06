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
package com.frojasg1.general.desktop.view.zoom.componentcopier.imp;

import com.frojasg1.general.desktop.view.zoom.componentcopier.CompCopier;
import com.frojasg1.general.desktop.view.zoom.componentcopier.CompCopierBase;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPasswordField;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class JPasswordFieldCopier extends CompCopierBase<JPasswordField>
{

	@Override
	protected List<CompCopier<JPasswordField>> createCopiers() {

		List<CompCopier<JPasswordField>> result = new ArrayList<>();

		result.add( createEchoCharCopier() );

		return( result );
	}

	protected CompCopier<JPasswordField> createEchoCharCopier()
	{
		return( (originalComponent, newComponent) -> copyEchoChar( originalComponent, newComponent ) );
	}

	@Override
	public Class<JPasswordField> getParameterClass() {
		return( JPasswordField.class );
	}

	protected void copyEchoChar( JPasswordField originalComponent, JPasswordField newComponent )
	{
		newComponent.setEchoChar( originalComponent.getEchoChar() );
	}
}
