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
package com.frojasg1.general.desktop.execution.whatisnew;

import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.application.version.ApplicationVersion;
import com.frojasg1.general.desktop.view.whatisnew.WhatIsNewJDialogBase;

/**
 *
 * @author fjavier.rojas
 */
public class WhatIsNewExecution implements Runnable
{
	protected WhatIsNewJDialogBase _dialog;
	protected BaseApplicationConfigurationInterface _applicationConfiguration;
	protected Boolean _hasToShow = null;

	public WhatIsNewExecution( BaseApplicationConfigurationInterface applicationConfiguration )
	{
		this(new WhatIsNewJDialogBase( applicationConfiguration ),
				applicationConfiguration );
	}

	public WhatIsNewExecution( WhatIsNewJDialogBase dialog,
								BaseApplicationConfigurationInterface applicationConfiguration )
	{
		this( dialog, applicationConfiguration, null );
	}

	public WhatIsNewExecution( WhatIsNewJDialogBase dialog,
								BaseApplicationConfigurationInterface applicationConfiguration,
								Boolean hasToShow )
	{
		_dialog = dialog;
		_applicationConfiguration = applicationConfiguration;
		_hasToShow = hasToShow;
	}

	public boolean hasToShow()
	{
		Boolean result = _hasToShow;

		if( result == null )
		{
			String currentDownloadFile = ApplicationVersion.instance().getDownloadFile();

			if( currentDownloadFile != null )
			{
				boolean hasBeenShownPreviuosly = _applicationConfiguration.hasBeenShownWhatIsNewOfDownloadFile(currentDownloadFile);

				result = ( !hasBeenShownPreviuosly && _dialog.documentExists() );
			}
		}

		return( result );
	}

	@Override
	public void run()
	{
		String currentDownloadFile = ApplicationVersion.instance().getDownloadFile();
		
		if( hasToShow() )
		{
			_dialog.setVisible(true);
			_applicationConfiguration.addDownloadFileWhatIsNewShown(currentDownloadFile);
		}
	}
}
