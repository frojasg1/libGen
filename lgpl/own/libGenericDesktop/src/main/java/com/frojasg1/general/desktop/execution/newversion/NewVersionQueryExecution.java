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
package com.frojasg1.general.desktop.execution.newversion;

import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.desktop.queries.newversion.NewVersionQuery;
import com.frojasg1.general.desktop.queries.newversion.NewVersionQueryFactory;
import com.frojasg1.general.desktop.queries.newversion.NewVersionQueryResult;
import com.frojasg1.general.desktop.view.newversion.NewVersionFoundJDialog;
import java.util.Objects;
import javax.swing.JFrame;

/**
 *
 * @author fjavier.rojas
 */
public class NewVersionQueryExecution implements Runnable
{
	protected BaseApplicationConfigurationInterface _applicationConfiguration;
	protected boolean _isStartQuery;
	protected boolean _onlyShowResultWhenThereIsANewDownloadableVersion = false;

	protected JFrame _parentJFrame = null;

	protected boolean _urlClicked = false;

	public NewVersionQueryExecution( JFrame parentJFrame,
									BaseApplicationConfigurationInterface applicationConfiguration,
									boolean isStartQuery,
									boolean onlyShowResultWhenThereIsANewDownloadableVersion )
	{
		_parentJFrame = parentJFrame;

		_applicationConfiguration = applicationConfiguration;
		_isStartQuery = isStartQuery;
		_onlyShowResultWhenThereIsANewDownloadableVersion = onlyShowResultWhenThereIsANewDownloadableVersion;
	}

	protected NewVersionQuery createNewVersionInetQueryForApplications()
	{
		return( NewVersionQueryFactory.instance().createNewVersionQuery() );
	}

	protected NewVersionFoundJDialog createNewVersionFoundJDialog( NewVersionQueryResult result )
	{
		return( new NewVersionFoundJDialog( _parentJFrame, true, _applicationConfiguration, result ) );
	}

	protected boolean isIgnored( NewVersionQueryResult nvqr )
	{
		boolean result = false;
		
		if( nvqr.getNewDownloadResource() != null )
		{
			result = Objects.equals(nvqr.getNewDownloadResource(), _applicationConfiguration.getDownloadFileToIgnore() );
		}

		return( result );
	}

	@Override
	public void run()
	{
		try
		{
			NewVersionQuery nvq = createNewVersionInetQueryForApplications();
			NewVersionQueryResult result = nvq.queryForApplication(_isStartQuery);

			if( ( result != null ) &&
				( ! _onlyShowResultWhenThereIsANewDownloadableVersion ||
					( result.isSuccessful() ) && result.thereIsANewVersion() &&
					!isIgnored( result )
				)
			  )
			{
				NewVersionFoundJDialog dialog = createNewVersionFoundJDialog( result );

				dialog.setVisible(true);

				_urlClicked = dialog.wasUrlClicked();
				if( _urlClicked )
				{
					urlClicked();
				}
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	public boolean wasUrlClicked()
	{
		return( _urlClicked );
	}

	protected void urlClicked()
	{
	}
}
