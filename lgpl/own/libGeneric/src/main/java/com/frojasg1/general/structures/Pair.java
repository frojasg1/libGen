/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.structures;

import java.util.Objects;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class Pair<KK, VV>
{
	protected KK _key = null;
	protected VV _value = null;

	public Pair( KK key, VV value )
	{
		_key = key;
		_value = value;
	}

	public KK getKey()
	{
		return( _key );
	}

	public VV getValue()
	{
		return( _value );
	}

	public void setKey( KK key )
	{
		_key = key;
	}

	public void setValue( VV value )
	{
		_value = value;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (!Objects.equals(this._key, other._key)) {
			return false;
		}
		if (!Objects.equals(this._value, other._value)) {
			return false;
		}
		return true;
	}


}
