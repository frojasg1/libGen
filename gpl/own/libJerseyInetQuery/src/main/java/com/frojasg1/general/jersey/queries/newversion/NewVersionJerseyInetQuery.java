/* 
 * Copyright (C) 2020 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/gpl-3.0.txt
 *
 */
package com.frojasg1.general.jersey.queries.newversion;

import com.frojasg1.general.BooleanFunctions;
import com.frojasg1.general.FileFunctions;
import com.frojasg1.general.desktop.queries.InetQueryException;
import com.frojasg1.general.desktop.queries.newversion.NewVersionQuery;
import com.frojasg1.general.desktop.queries.newversion.NewVersionQueryResult;
import com.frojasg1.general.jersey.queries.InetQueryBase;
import org.json.JSONObject;

/**
 *
 * @author fjavier.rojas
 */
public abstract class NewVersionJerseyInetQuery extends InetQueryBase<NewVersionQueryResult>
								implements NewVersionQuery
{
//	protected static final String URL = "https://frojasg1.com:8080/downloads_web";
	protected static final String PATH = "/restful/versionQueryQueryInput";

	protected static final String VERSION_OF_SERVICE_PARAM = "versionOfService";
	protected static final String VERSION_OF_SERVICE_VALUE = "1.0";

	protected static final String DOWNLOAD_FILE_PARAM = "downloadFile";
	protected static final String IS_APPLICATION_START_PARAM = "isApplicationStart";
	protected static final String APPLICATION_LANGUAGE_PARAM = "applicationLanguage";
	protected static final String WEB_LANGUAGE_PARAM = "webLanguage";

	@Override
	public void init( String url )
	{
		super.init( url, PATH );
	}

	@Override
	protected NewVersionQueryResult createInetQueryResult()
	{
		return( new NewVersionQueryResult() );
	}

	@Override
	public abstract NewVersionQueryResult queryForApplication( boolean isApplicationStart ) throws InetQueryException;

	protected String getBaseFileName( String longFileName )
	{
		return( FileFunctions.instance().getBaseName(longFileName) );
	}

	protected NewVersionQueryResult createNewVersionQueryResult( JSONObject jsonObject )
	{
		NewVersionQueryResult result = createInetQueryResult();

		boolean successful = jsonObject.getBoolean( "successful" );
		result.setSuccessful( successful );
		if( successful )
		{
			boolean thereIsANewVersion = jsonObject.getBoolean( "thereIsANewVersion" );
			result.setThereIsANewVersion( thereIsANewVersion );

			if( thereIsANewVersion )
			{
				result.setIsAFinalVersion(jsonObject.getBoolean( "isAFinalVersion" ) );
				result.setLink( jsonObject.getString( "link" ) );
				result.setHintForDownload(jsonObject.getString( "hintForDownload" ) );
				result.setNewDownloadResource( getBaseFileName( jsonObject.getString( "newDownloadResource" ) ) );

				result.setNumberOfDownloadsOfLatestVersion(jsonObject.getInt( "numberOfDownloadsOfLatestVersion" ) );
				result.setTotalNumberOfDownloadsOfApplication(jsonObject.getInt( "totalNumberOfDownloadsOfApplication" ) );
			}
		}
		else
		{
			result.setErrorString( jsonObject.getString( "errorString" ) );
		}

		return( result );
	}

	@Override
	protected NewVersionQueryResult convert( String jsonStr )
	{
		NewVersionQueryResult result = null;
		
		try
		{
			result = createNewVersionQueryResult( getJsonObject(jsonStr) );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return( result );
	}

	public NewVersionQueryResult query( String downloadFile, boolean isApplicationStart,
										String applicationLanguage,
										String webLanguage ) throws InetQueryException
	{
		return( queryGen( VERSION_OF_SERVICE_PARAM, VERSION_OF_SERVICE_VALUE,
						DOWNLOAD_FILE_PARAM, downloadFile,
						IS_APPLICATION_START_PARAM, BooleanFunctions.instance().booleanToString(isApplicationStart),
						APPLICATION_LANGUAGE_PARAM, applicationLanguage,
						WEB_LANGUAGE_PARAM, webLanguage ) );
	}
}
