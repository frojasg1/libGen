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
package com.frojasg1.general.desktop.view.whatisnew;

import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.desktop.view.license.GenericLicenseJDialog;
import javax.swing.JFrame;

/**
 *
 * @author fjavier.rojas
 */
public class WhatIsNewJDialogBase extends GenericLicenseJDialog
{
	protected static final String _configurationBaseFileName = "WhatIsNewJDialog";
	public static final String BASE_RTF_FILE_NAME = "What.is.new.rtf";

	public WhatIsNewJDialogBase( BaseApplicationConfigurationInterface appConf )
	{
		this( null, appConf );
	}

	public WhatIsNewJDialogBase( JFrame parent, BaseApplicationConfigurationInterface appConf )
	{
		this( parent, appConf, BASE_RTF_FILE_NAME, _configurationBaseFileName );
	}

	public WhatIsNewJDialogBase( JFrame parent, BaseApplicationConfigurationInterface appConf,
									String singleRtfFileName,
									String baseConfigurationFileName )
	{
		super( parent, appConf, singleRtfFileName, false, baseConfigurationFileName, true );
		setTitle("");
	}
}
