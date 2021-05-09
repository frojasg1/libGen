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
package com.frojasg1.general.desktop.view;

import com.frojasg1.applications.common.components.data.ComponentData;
import com.frojasg1.applications.common.components.data.MapOfComponents;
import com.frojasg1.applications.common.components.internationalization.JFrameInternationalization;
import com.frojasg1.applications.common.components.internationalization.window.InternationalizationOwner;
import com.frojasg1.applications.common.components.internationalization.window.InternationalizedWindow;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.ClassFunctions;
import com.frojasg1.general.NullFunctions;
import com.frojasg1.general.desktop.view.color.ColorInversor;
import com.frojasg1.general.desktop.view.color.ColorThemeChangeableStatus;
import com.frojasg1.general.desktop.view.color.factory.impl.ColorInversorFactoryImpl;
import com.frojasg1.generic.GenericFunctions;
import java.awt.Component;
import java.util.function.Function;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class FrameworkComponentFunctions
{
	protected static FrameworkComponentFunctions _instance;

	protected ColorInversor _defaultColorInversor;

	public static void changeInstance( FrameworkComponentFunctions inst )
	{
		_instance = inst;
	}

	public static FrameworkComponentFunctions instance()
	{
		if( _instance == null )
			_instance = new FrameworkComponentFunctions();
		return( _instance );
	}

	public <CC> CC getOwnOrFirstParentOfClass( Component comp, Class<CC> clazz )
	{
		CC result = ClassFunctions.instance().cast(comp, clazz);
		if( result == null )
			result = ComponentFunctions.instance().getFirstParentInstanceOf( clazz, comp );

		return( result );
	}

	public InternationalizationOwner getInternationalizationOwner( Component comp )
	{
		return( getOwnOrFirstParentOfClass( comp, InternationalizationOwner.class ) );
	}

	public InternationalizedWindow getInternationalizedWindow( Component comp )
	{
		return( getOwnOrFirstParentOfClass( comp, InternationalizedWindow.class ) );
	}

	protected <CC, RR> RR getIfNotNull( CC obj, Function<CC,RR> getter )
	{
		return( NullFunctions.instance().getIfNotNull(obj, getter) );
	}

	protected ColorInversor getDefaultColorInversor()
	{
		if( _defaultColorInversor == null )
			_defaultColorInversor = ColorInversorFactoryImpl.instance().createColorInversor();

		return( _defaultColorInversor );
	}

	public ColorInversor getColorInversor(Component comp)
	{
		ColorInversor result = getIfNotNull(  getInternationalizedWindow( comp ),
								InternationalizedWindow::getColorInversor );
		if( result == null )
			result = getDefaultColorInversor();

		return( result );
	}

	public boolean isDarkModeActivated(Component comp)
	{
		Boolean result = isDarkModeInternal(comp);

		if( result == null )
			result = getIfNotNull( GenericFunctions.instance().getAppliConf(),
								BaseApplicationConfigurationInterface::isDarkModeActivated );

		if( result == null )
			result = false;

		return( result );
	}

	public MapOfComponents getMapOfComponents(Component comp)
	{
		return( getIfNotNull(
						getIfNotNull(  getInternationalizationOwner( comp ),
										InternationalizationOwner::getInternationalization ),
							JFrameInternationalization::getMapOfComponents ) );
	}

	public ComponentData getComponentData(Component comp)
	{
		return( getIfNotNull( getMapOfComponents(comp),	moc -> moc.get(comp) ) );
	}

	public ComponentData getComponentDataOnTheFly(Component comp)
	{
		return( getIfNotNull( getMapOfComponents(comp),	moc -> moc.getOrCreateOnTheFly(comp) ) );
	}

	public ColorThemeChangeableStatus getColorThemeChangeableStatus( Component comp )
	{
		return( getIfNotNull( getComponentData( comp ), ComponentData::getColorThemeChangeableStatus ) );
	}

	protected Boolean isColorChangeableBooleanAttribute( Component comp,
		Function<ColorThemeChangeableStatus, Boolean> getter )
	{
		return( getIfNotNull( getColorThemeChangeableStatus( comp ), getter ) );
	}

	public Boolean isDarkModeInternal(Component comp)
	{
		return( isColorChangeableBooleanAttribute( comp, ColorThemeChangeableStatus::isDarkMode ) );
	}

	protected boolean isColorThemeChangeableStatusBooleanWithDefault(Component comp,
		Function<ColorThemeChangeableStatus, Boolean> getter, boolean defaultValue)
	{
		Boolean result = isColorChangeableBooleanAttribute(comp, getter);

		if( result == null )
			result = defaultValue;

		return( result );
	}

	public boolean isDarkMode(Component comp)
	{
		return( isColorThemeChangeableStatusBooleanWithDefault( comp,
			ColorThemeChangeableStatus::isDarkMode, false ) );
	}

	public boolean wasLatestModeDark(Component comp)
	{
		return( isColorThemeChangeableStatusBooleanWithDefault( comp,
			ColorThemeChangeableStatus::wasLatestModeDark, false ) );
	}
}
