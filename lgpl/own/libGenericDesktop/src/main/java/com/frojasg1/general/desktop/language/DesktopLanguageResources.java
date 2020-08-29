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
package com.frojasg1.general.desktop.language;

import com.frojasg1.applications.common.components.internationalization.JFrameInternationalization;
import com.frojasg1.applications.common.components.internationalization.window.InternationalizedWindowFunctions;
import com.frojasg1.general.desktop.GenericDesktopConstants;
import com.frojasg1.general.desktop.dialogs.highlevel.HighLevelDialogs;
import com.frojasg1.general.desktop.view.newversion.NewVersionFoundJDialog;
import com.frojasg1.general.language.LanguageResources;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class DesktopLanguageResources extends LanguageResources
{
	protected static DesktopLanguageResources _instance;

	public static DesktopLanguageResources instance()
	{
		if( _instance == null )
		{
			_instance = new DesktopLanguageResources();
			LanguageResources.instance().addLanguageResource(_instance);
		}
		return( _instance );
	}

	@Override
	public void copyOwnLanguageConfigurationFilesFromJar( String newFolder )
	{
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", "CopyPastePopupMenu.properties" );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", "CustomizedJPasswordField.properties" );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", "DesktopBinaryFile.properties" );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", JFrameInternationalization.GLOBAL_CONF_FILE_NAME );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", "searchAndReplaceJFrame_LAN.properties" );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", NewVersionFoundJDialog.sa_configurationBaseFileName + "_LAN.properties" );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", InternationalizedWindowFunctions.GLOBAL_CONF_FILE_NAME );
		copyLanguageConfigurationFileFromJarToFolder( newFolder, "EN", HighLevelDialogs.GLOBAL_CONF_FILE_NAME );
	}

	protected String getPropertiesPathInJar()
	{
		return( GenericDesktopConstants.sa_PROPERTIES_PATH_IN_JAR );
	}
}
