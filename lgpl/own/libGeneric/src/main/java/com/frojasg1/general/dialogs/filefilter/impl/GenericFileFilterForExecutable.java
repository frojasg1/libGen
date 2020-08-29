/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.dialogs.filefilter.impl;

import com.frojasg1.applications.common.configuration.InternationalizedStringConf;
import com.frojasg1.general.dialogs.filefilter.GenericFileFilter;
import java.io.File;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class GenericFileFilterForExecutable implements GenericFileFilter
{
	protected static final String CONF_DESCRIPTION = GenericFileFilterChooserImpl.CONF_GENERIC_FILE_FILTER_FOR_EXECUTABLE_BY_ATTRIBUTES_DESCRIPTION;
	protected InternationalizedStringConf _internationalStrings = null;

	public GenericFileFilterForExecutable( InternationalizedStringConf internationalStrings )
	{
		_internationalStrings = internationalStrings;
	}

	@Override
	public boolean accept(File file)
	{
		return( file.canRead() && file.canExecute() );
	}

	@Override
	public String getDescription()
	{
		return( _internationalStrings.getInternationalString(CONF_DESCRIPTION) );
	}
}
