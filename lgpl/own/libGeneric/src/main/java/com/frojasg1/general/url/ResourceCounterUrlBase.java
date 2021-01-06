/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.url;

import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.generic.GenericFunctions;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ResourceCounterUrlBase {

	public BaseApplicationConfigurationInterface getAppliConf()
	{
		return( GenericFunctions.instance().getAppliConf() );
	}

	protected String getApplicationNameResourceBase()
	{
		return( GenericFunctions.instance().getApplicationFacilities().getApplicationVersion() );
	}
}
