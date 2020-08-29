/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.dialogs.filefilter;

import java.io.File;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface GenericFileFilter
{
	public boolean accept( File file );
	public String getDescription();
}
