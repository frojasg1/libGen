/* 
 * Copyright (C) 2020 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/gpl-3.0.txt
 *
 */
package com.frojasg1.general.desktop.image;

import com.frojasg1.general.number.IntegerFunctions;

/**
 *
 * @author Usuario
 */
public class PixelComponents
{
	public static final int LUMINANCE = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int ALPHA = 4;

	public short _alpha;
	public short _red;
	public short _green;
	public short _blue;

	protected short _greyScale;
	protected int _argb;

	protected boolean _signedComponents = false;

	public PixelComponents( int argb, boolean signedComponents )
	{
		setArgb( argb, signedComponents );
	}

	public PixelComponents( short alpha, short red, short green, short blue,
							boolean signedComponents )
	{
		setComponents( alpha, red, green, blue, signedComponents );
	}

	public short getComponent( int componentIndex )
	{
		short result = Short.MIN_VALUE;

		switch( componentIndex )
		{
			case LUMINANCE:	result = getGreyScale(); break;
			case RED:	result = getRed(); break;
			case GREEN:	result = getGreen(); break;
			case BLUE:	result = getBlue(); break;
			case ALPHA:	result = getAlpha(); break;
		}

		return( result );
	}

	public void setComponent( int componentIndex, short value )
	{
		switch( componentIndex )
		{
			case LUMINANCE: setGreyScale(value); break;
			case RED:	setRed(value); break;
			case GREEN:	setGreen(value); break;
			case BLUE:	setBlue(value); break;
			case ALPHA:	setAlpha(value); break;
		}
	}

	public void setComponentWithoutLimit( int componentIndex, short value )
	{
		switch( componentIndex )
		{
			case LUMINANCE: _red = value; _green = value; _blue = value; break;
			case RED:	_red = value; break;
			case GREEN:	_green = value; break;
			case BLUE:	_blue = value; break;
			case ALPHA:	_alpha = value; break;
		}
	}

	public void setComponents( short alpha, short red, short green, short blue, boolean signedComponents )
	{
		_signedComponents = signedComponents;

		_alpha = limit( alpha );
		_red = limit( red );
		_green = limit( green );
		_blue = limit( blue );

		recalculateArgbAndGreyScale();

//		_greyScale = limit( from, to, _greyScale );
	}

	protected void recalculateArgbAndGreyScale()
	{
		_argb = ImageFunctions.instance().getARGB( absoluteValue( _alpha ),
			absoluteValue( _red ), absoluteValue( _green ),
			absoluteValue( _blue ) );

		_greyScale = ImageFunctions.instance().getGrayScale(_argb);
		if( _signedComponents )
			_greyScale = makeSigned( _greyScale );
	}

	protected int absoluteValue( int value )
	{
		return( _signedComponents ? value + 128 : value );
	}

	protected short limit( short value )
	{
		short from = 0;
		short to = 255;
		if( _signedComponents )
		{
			from = (short) -128;
			to = (short) 127;
		}

		short result = limit( from, to, value );

		return( result );
	}

	protected short limit( short from, short to, short value )
	{
		short result = value;
		if( result > to )
			result = to;
		if( result < from )
			result = from;

		return( result );
	}

	protected void makeComponentsSigned()
	{
		_alpha = makeSigned( _alpha );
		_red = makeSigned( _red );
		_green = makeSigned( _green );
		_blue = makeSigned( _blue );
		_greyScale = makeSigned( _greyScale );
	}

	protected short makeSigned( short value )
	{
		return( (short) ( value - 128 ) );
	}

	public void setArgb( int argb, boolean signedComponents )
	{
		_signedComponents = signedComponents;

		_greyScale = ImageFunctions.instance().getGrayScale(argb);
		_argb = argb;
		_alpha = (short) ( ( argb >>> 24 ) & 0xff );
		_red = (short) ( ( argb >>> 16 ) & 0xff );
		_green = (short) ( ( argb >>> 8 ) & 0xff );
		_blue = (short) ( argb & 0xff );

		if( _signedComponents )
			makeComponentsSigned();
	}

	public short getAlpha()
	{
		return( _alpha );
	}

	public short getRed()
	{
		return( _red );
	}
	
	public short getGreen()
	{
		return( _green );
	}
	
	public short getBlue()
	{
		return( _blue );
	}

	public void setAlpha( short value )
	{
		_alpha = limit( value );
		recalculateArgbAndGreyScale();
	}

	public void setGreyScale( short value )
	{
		setRed( value );
		setGreen( value );
		setBlue( value );
	}

	public void setRed( short value )
	{
		_red = limit( value );
		recalculateArgbAndGreyScale();
	}

	public void setGreen( short value )
	{
		_green = limit( value );
		recalculateArgbAndGreyScale();
	}

	public void setBlue( short value )
	{
		_blue = limit( value );
		recalculateArgbAndGreyScale();
	}


	public short getGreyScale()
	{
		return( _greyScale );
	}

	public boolean nearlyEquals( short[] pixelComponents, int tolerance )
	{
		boolean result = false;

		if( pixelComponents != null )
		{
			int redDiff = IntegerFunctions.abs( pixelComponents[0] - getRed() );
			int greenDiff = IntegerFunctions.abs( pixelComponents[1] - getGreen() );
			int blueDiff = IntegerFunctions.abs( pixelComponents[2] - getBlue() );

			if( ( getRed() >= 0 ) && ( redDiff <= tolerance ) &&
				( getGreen() >= 0 ) && ( greenDiff <= tolerance ) &&
				( getBlue() >= 0 ) && ( blueDiff <= tolerance ) )
			{
				result = true;
			}
		}

		return( result );
	}

	public int getPixelValue()
	{
		return( _argb );
	}

	public PixelComponents subtract( PixelComponents other )
	{
		PixelComponents result = new PixelComponents( 0, _signedComponents );

		result._alpha = (short) ( _alpha - other._alpha );
		result._red = (short) ( _red - other._red );
		result._green = (short) ( _green - other._green );
		result._blue = (short) ( _blue - other._blue );

		result.setComponents(result._alpha, result._red, result._green, result._blue, _signedComponents);

		return( result );
	}
}
