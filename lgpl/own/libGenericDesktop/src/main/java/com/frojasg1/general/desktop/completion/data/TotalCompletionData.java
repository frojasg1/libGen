/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.completion.data;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class TotalCompletionData<LL> {
	protected AlternativesForCompletionData<LL> _alternativesForCompletionData;
	protected CurrentParamForCompletionData<LL> _currentParamForCompletionData;
	protected boolean _hasToSetFocus;

	public AlternativesForCompletionData<LL> createAndSetAlternativesForCompletionData()
	{
		AlternativesForCompletionData<LL> result = new AlternativesForCompletionData<>();
		setAlternativesForCompletionData(result);

		return( result );
	}

	public void setAlternativesForCompletionData( AlternativesForCompletionData<LL> value )
	{
		_alternativesForCompletionData = value;
	}

	public AlternativesForCompletionData<LL> getAlternativesForCompletionData()
	{
		return( _alternativesForCompletionData );
	}

	public CurrentParamForCompletionData<LL> createAndSetCurrentParamForCompletionData()
	{
		CurrentParamForCompletionData<LL> result = new CurrentParamForCompletionData<>();
		setCurrentParamForCompletionData( result );

		return( result );
	}

	public void setCurrentParamForCompletionData( CurrentParamForCompletionData<LL> value )
	{
		_currentParamForCompletionData = value;
	}

	public CurrentParamForCompletionData<LL> getCurrentParamForCompletionData()
	{
		return( _currentParamForCompletionData );
	}

	public void setHasToSetFocus( boolean value )
	{
		_hasToSetFocus = value;
	}

	public boolean hasToSetFocus()
	{
		return( _hasToSetFocus );
	}
}
