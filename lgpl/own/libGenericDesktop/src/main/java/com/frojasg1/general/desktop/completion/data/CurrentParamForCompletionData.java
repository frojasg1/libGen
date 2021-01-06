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
public class CurrentParamForCompletionData<LL> {
	protected PrototypeForCompletionBase _prototype;
	protected int _currentParamIndex;
	protected LL _locationControl;

	public PrototypeForCompletionBase getPrototype()
	{
		return( _prototype );
	}

	public void setPrototype(PrototypeForCompletionBase value)
	{
		_prototype = value;
	}

	public int getCurrentParamIndex()
	{
		return( _currentParamIndex );
	}

	public void setCurrentParamIndex( int value )
	{
		_currentParamIndex = value;
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
