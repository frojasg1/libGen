/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.generic.application;

import com.frojasg1.general.desktop.application.version.DesktopApplicationVersion;
import com.frojasg1.generic.application.ApplicationFacilitiesBase;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class DesktopApplicationFacilities extends ApplicationFacilitiesBase
{
	protected static DesktopApplicationFacilities _instance = null;

	public static DesktopApplicationFacilities instance()
	{
		if( _instance == null )
			_instance = new DesktopApplicationFacilities();

		return( _instance );
	}

	@Override
	public String getApplicationVersion()
	{
		return( DesktopApplicationVersion.instance().getDownloadFile() );
	}
}
