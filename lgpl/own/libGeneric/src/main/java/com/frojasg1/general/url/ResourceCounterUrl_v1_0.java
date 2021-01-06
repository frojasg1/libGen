/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.url;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ResourceCounterUrl_v1_0 extends ResourceCounterUrlBase
									implements ResourceCounterUrl
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceCounterUrl_v1_0.class);

	protected static ResourceCounterUrl_v1_0 _instance = null;
	
	public static ResourceCounterUrl_v1_0 instance()
	{
		if( _instance == null )
			_instance = new ResourceCounterUrl_v1_0();

		return( _instance );
	}

	public String createResourceCounterUrl( String realUrl )
	{
		String result = null;

		try
		{
			URIBuilder builder = new URIBuilder( getAppliConf().getUrlForResourceCounter() )
				.addParameter( "operation", "countAndForward" )
				.addParameter( "url", realUrl )
				.addParameter( "origin", "appli" )
				.addParameter( "versionOfService", "v1.0" )
				.addParameter( "applicationBaseResourceName", getApplicationNameResourceBase() )
				.addParameter( "applicationLanguage", getAppliConf().getLanguage() );

			result = builder.build().toASCIIString();
		}
		catch( Exception ex )
		{
			LOGGER.error( "Error creating ResourceCounterUrl", ex );
		}

		return( result );
	}
}
