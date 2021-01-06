/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.completion.data;

import com.frojasg1.general.completion.PrototypeForCompletionBase;
import java.awt.Rectangle;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class AlternativesForCompletionData<LL>
{
	protected String _preText;
	protected PrototypeForCompletionBase[] _prototypes;
	protected LL _locationControl;

	public PrototypeForCompletionBase[] getPrototypes()
	{
		return( _prototypes );
	}

	public void setPrototypes(PrototypeForCompletionBase[] array)
	{
		_prototypes = array;
	}

	public String getPreText()
	{
		return( _preText );
	}

	public void setPreText( String value )
	{
		_preText = value;
	}

	public LL getLocationControl()
	{
		return( _locationControl );
	}

	public void setLocationControl(LL value)
	{
		_locationControl = value;
	}
}
