/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.dialogs.implementation.filefilter.impl;

import com.frojasg1.general.desktop.dialogs.implementation.filefilter.DesktopGenericFileFilter;
import com.frojasg1.general.dialogs.filefilter.GenericFileFilter;
import java.io.File;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class DesktopGenericFileFilterImpl extends DesktopGenericFileFilter
{
	protected GenericFileFilter _genericFileFilter = null;

	public DesktopGenericFileFilterImpl( GenericFileFilter genericFileFilter )
	{
		_genericFileFilter = genericFileFilter;
	}

	@Override
	public boolean accept(File pathname)
	{
		return( _genericFileFilter.accept( pathname ) );
	}

	@Override
	public String getDescription()
	{
		return( _genericFileFilter.getDescription() );
	}
}
