/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.generic.application;

import com.frojasg1.general.url.ResourceCounterUrl;
import com.frojasg1.general.url.ResourceCounterUrl_v1_0;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class ApplicationFacilitiesBase implements ApplicationFacilitiesInterface
{
	@Override
	public abstract String getApplicationVersion();

	protected ResourceCounterUrl getResourceCounterUrl()
	{
		return( ResourceCounterUrl_v1_0.instance() );
	}

	@Override
	public String buildResourceCounterUrl(String realUrl)
	{
		return( getResourceCounterUrl().createResourceCounterUrl(realUrl) );
	}
}
