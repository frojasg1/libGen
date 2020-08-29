/* 
 * Copyright (C) 2020 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package com.frojasg1.general.reflection;

import com.frojasg1.general.ExecutionFunctions;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ReflectionFunctions
{
	protected static ReflectionFunctions _instance;

	protected Map< String, Map< String, Field > > _mapOfMapsOfFields = new HashMap<>();
	protected Map< String, Map< String, Method > > _mapOfMapsOfMethods = new HashMap<>();

	protected Map< Class<?>, Map< Class<?>, Boolean > > _mapOfSuperClasses = new HashMap();

	public static void changeInstance( ReflectionFunctions inst )
	{
		_instance = inst;
	}

	public static ReflectionFunctions instance()
	{
		if( _instance == null )
			_instance = new ReflectionFunctions();
		return( _instance );
	}

	protected Map< String, Field > inspectFields( Class<?> clazz )
	{
		Map< String, Field > result = new HashMap<>();

		List<Field> fields = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields())
		{
			field.setAccessible(true);
			result.put( field.getName(), field );
		}

		return( result );
	}

	protected Map< String, Method > inspectMethods( Class<?> clazz )
	{
		Map< String, Method > result = new HashMap<>();

		List<Method> methods = new ArrayList<>();
		for (Method method : clazz.getDeclaredMethods())
		{
			method.setAccessible(true);
			result.put( method.getName(), method );
		}

		return( result );
	}

	public Map< String, Field > getFieldsMap( Class<?> clazz )
	{
		Map< String, Field > result = null;
		if( clazz != null )
		{
			result = _mapOfMapsOfFields.get( clazz.getName() );

			if( result == null )
			{
				result = inspectFields( clazz );

				_mapOfMapsOfFields.put( clazz.getName(), result );
			}
		}

		return( result );
	}

	public Map< String, Method > getMethodsMap( Class<?> clazz )
	{
		Map< String, Method > result = null;
		if( clazz != null )
		{
			result = _mapOfMapsOfMethods.get( clazz.getName() );

			if( result == null )
			{
				result = inspectMethods( clazz );

				_mapOfMapsOfMethods.put( clazz.getName(), result );
			}
		}

		return( result );
	}

	public <T> T getStaticAttribute( String attributeName, Class<T> returnClazz,
									Class<?> classOfTheAttribute )
	{
		return( getAttribute( attributeName, returnClazz, null, classOfTheAttribute ) );
	}

	public <T> T getAttribute( String attributeName, Class<T> returnClazz,
								Object object )
	{
		if( object == null )
			return( null );

		return( getAttribute( attributeName, returnClazz, object, object.getClass() ) );
	}

	protected Field getField( String attributeName, Class<?> clazz )
	{
		Map< String, Field > map = getFieldsMap( clazz );

		Field result = map.get( attributeName );

		return( result );
	}

	protected Method getMethod( String methodName, Class<?> clazz )
	{
		Map< String, Method > map = getMethodsMap( clazz );

		Method result = map.get( methodName );

		return( result );
	}

	public Object invokeClassMethod( String methodName, Class<?> clazz, Object obj, Object ... args )
	{
		Object result = null;
		Method method = getMethod( methodName, clazz );
		if( method != null )
			result = invokeMethod( methodName, method, obj, args );

		return( result );
	}

	public Object invokeMethod( String methodName, Object obj, Object ... args )
	{
		Object result = null;

//		if( obj != null )
		{
			List<Class<?>> classList = getListOfClassesOfObjectFromTheMostGenericToTheMostParticular( obj );

			Collections.reverse(classList);

			for( Class<?> clazz: classList )
			{
				invokeClassMethod( methodName, clazz, obj, args );
/*
				Method method = getMethod( methodName, clazz );
				if( method != null )
					result = invokeMethod( methodName, method, obj, args );
*/
			}
		}

		return( result );
	}

	public Object invokeStaticMethod( String methodName, Class<?> clazz, Object ... args )
	{
		Method method = getMethod( methodName, clazz );
		
		return( invokeMethod( methodName, method, null, args ) );
	}

	public Object invokeMethod( String methodName, Class<?> clazz, Object obj, Object ... args )
	{
		Method method = getMethod( methodName, clazz );
		
		return( invokeMethod( methodName, method, obj, args ) );
	}

	protected boolean isStatic( Method method )
	{
		boolean result = false;

		if( method != null )
			result = ( method.getModifiers() | Modifier.STATIC ) != 0;

		return( result );
	}

	protected Object invokeMethod( String methodName, Method method, Object obj, Object ... args )
	{
		Object result = null;

		try
		{
			if( ( method != null ) && !( !isStatic( method ) && ( obj == null ) ) )
				result = method.invoke(obj, args);
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return( result );
	}

	public <T> T getAttribute( String attributeName, Class<T> returnClazz,
								Object object, Class<?> objectClass )
	{
		T result = null;

		if( //( object != null ) &&
			( returnClazz != null ) &&
			( attributeName != null ) &&
			( objectClass != null ) )
		{
			Field field = getField( attributeName, objectClass );

			if( field != null )
			{
				Object resObj = null;
				try
				{
					resObj = field.get(object);

					if( returnClazz.isInstance( resObj ) )
					{
						result = (T) resObj;
					}
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				}
			}
		}

		return( result );
	}

	public <T> boolean setAttribute( String attributeName,
										Object object, Class<?> objectClass,
										T value )
	{
		boolean result = false;

		Field field = getField( attributeName, objectClass );
		if( field != null )
		{
//			if( ( value == null ) || ( field.getType().isInstance( value ) ) )
			{
				try
				{
					field.set( object, value );
					result = true;
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				}
			}
		}

		return( result );
	}

	public boolean isSuperClass( Class<?> clazz, Class<?> probablyParentClass )
	{
		Boolean result = getMapIsSuperClass( clazz, probablyParentClass );

		if( result == null )
		{
			result = calculateIsSuperClass( clazz, probablyParentClass );

			putInMapOfSuperClasses( clazz, probablyParentClass, result );
		}

		return( result );
	}

	protected boolean calculateIsSuperClass( Class<?> clazz, Class<?> probablyParentClass )
	{
		boolean result = false;

		if( ( clazz != null ) && ( probablyParentClass != null ) )
		{
			String parentClassName = probablyParentClass.getName();
			do
			{
				result = clazz.getName().equals( parentClassName );

				clazz = clazz.getSuperclass();
			}
			while( ( clazz != null ) && !result );
		}

		return( result );
	}

	public Boolean getMapIsSuperClass( Class<?> clazz, Class<?> probablyParentClass )
	{
		Boolean result = null;

		Map< Class<?>, Boolean > tmpMap = _mapOfSuperClasses.get( clazz );

		if( tmpMap != null )
			result = tmpMap.get( probablyParentClass );

		return( result );
	}

	public void putInMapOfSuperClasses( Class<?> clazz, Class<?> probablyParentClass, boolean value )
	{
		Map< Class<?>, Boolean > tmpMap = _mapOfSuperClasses.get( clazz );

		if( tmpMap == null )
		{
			tmpMap = new HashMap<>();
			_mapOfSuperClasses.put( clazz, tmpMap );
		}

		tmpMap.put( probablyParentClass, value );
	}

	public List<Class<?>> getListOfClassesOfObjectFromTheMostGenericToTheMostParticular( Object object )
	{
		List<Class<?>> result = new ArrayList<>();
		
		if( object != null )
		{
			Class<?> currentClass = object.getClass();
			do
			{
				result.add( currentClass );
				currentClass = currentClass.getSuperclass();
			}
			while( currentClass != null );

			Collections.reverse(result);
		}

		return( result );
	}

	// https://stackoverflow.com/questions/203475/how-do-i-identify-immutable-objects-in-java
	public boolean isImmutable(Object obj)
	{
		Class<?> objClass = obj.getClass();

		if( String.class.equals( objClass ) )
			return( true );
		else if( Integer.class.equals( objClass ) )
			return( true );
		else if( Long.class.equals( objClass ) )
			return( true );
		else if( Double.class.equals( objClass ) )
			return( true );
		else if( Float.class.equals( objClass ) )
			return( true );
		else if( Boolean.class.equals( objClass ) )
			return( true );

		// Class of the object must be a direct child class of the required class
		Class<?> superClass = objClass.getSuperclass();
		if (Immutable.class.equals(superClass)) {
			return true;
		}

		// Class must be final
		if (!Modifier.isFinal(objClass.getModifiers())) {
			return false;
		}

		// Check all fields defined in the class for type and if they are final
		Field[] objFields = objClass.getDeclaredFields();
		for (int i = 0; i < objFields.length; i++) {
			if (!Modifier.isFinal(objFields[i].getModifiers())
				|| !isValidFieldType(objFields[i].getType())) {
					return false;
			}
		}

		// Lets hope we didn't forget something
		return true;
	}

	public boolean hasCopyConstructor( Object obj )
	{
		boolean result = false;
		if( obj != null )
			result = ( ExecutionFunctions.instance().safeSilentFunctionExecution( () -> obj.getClass().getConstructor( obj.getClass() ) ) != null );

		return( result );
	}

	public boolean isValidFieldType(Class<?> type) {
		// Check for all allowed property types...
		return type.isPrimitive() || String.class.equals(type);
	}
}
