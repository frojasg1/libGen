/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.commandline.generic.application;

import com.frojasg1.general.commandline.application.version.CommandLineApplicationVersion;
import com.frojasg1.generic.application.ApplicationFacilitiesBase;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class CommandLineApplicationFacilities extends ApplicationFacilitiesBase
{
	protected static CommandLineApplicationFacilities _instance = null;

	public static CommandLineApplicationFacilities instance()
	{
		if( _instance == null )
			_instance = new CommandLineApplicationFacilities();

		return( _instance );
	}

	@Override
	public String getApplicationVersion()
	{
		return( CommandLineApplicationVersion.instance().getDownloadFile() );
	}
}
