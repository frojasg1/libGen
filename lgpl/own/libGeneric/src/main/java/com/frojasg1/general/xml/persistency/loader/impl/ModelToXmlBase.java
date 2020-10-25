/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.xml.persistency.loader.impl;

import com.frojasg1.general.CollectionFunctions;
import com.frojasg1.general.DoubleFunctions;
import com.frojasg1.general.xml.XmlElement;
import com.frojasg1.general.xml.XmlFunctions;
import com.frojasg1.general.xml.persistency.loader.ModelToXml;
import java.util.List;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class ModelToXmlBase<CC> implements ModelToXml<CC>
{

	protected <CCC> List<CCC> reverse( List<CCC> input )
	{
		return( CollectionFunctions.instance().reverseList(input) );
	}

	protected XmlElement createElement( XmlElement parent, String name )
	{
		XmlElement result = createElement( name );
		parent.addChild( result );

		return( result );
	}

	protected XmlElement createElement( String name )
	{
		return( XmlFunctions.instance().createXmlElement( name ) );
	}

	protected XmlElement createLeafElement( String name, String text )
	{
		XmlElement result = createElement( name );
		result.setText( text );

		return( result );
	}

	protected XmlElement createLeafElement( XmlElement parent, String name, String text )
	{
		XmlElement result = createElement( name );
		result.setText( text );

		parent.addChild( result );

		return( result );
	}

	protected <CC> String toString( CC obj )
	{
		String result = "null";
		if( obj != null )
			result = obj.toString();

		return( result );
	}

	protected String format( double value )
	{
		return( DoubleFunctions.instance().format( value ) );
	}
}
