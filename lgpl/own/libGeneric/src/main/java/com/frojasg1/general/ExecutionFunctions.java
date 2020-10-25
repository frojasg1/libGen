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
package com.frojasg1.general;

import javax.swing.SwingUtilities;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ExecutionFunctions
{
	protected static ExecutionFunctions _instance;

	public static void changeInstance( ExecutionFunctions inst )
	{
		_instance = inst;
	}

	public static ExecutionFunctions instance()
	{
		if( _instance == null )
			_instance = new ExecutionFunctions();
		return( _instance );
	}

	public Exception safeSilentMethodExecution( UnsafeMethod run )
	{
		return( safeMethodExecution( run, false ) );
	}

	public Exception safeMethodExecution( UnsafeMethod run )
	{
		return( safeMethodExecution( run, true ) );
	}

	protected Exception safeMethodExecution( UnsafeMethod run, boolean traceException )
	{
		Exception result = null;
		try
		{
			if( ! Thread.currentThread().isInterrupted() )
				run.run();
		}
		catch (InterruptedException ex)
		{
			result = ex;
			Thread.currentThread().interrupt();
		}
		catch( Exception ex )
		{
			result = ex;
			if( traceException )
				ex.printStackTrace();
		}

		return( result );
	}

	public <CC> CC safeSilentFunctionExecution( UnsafeFunction<CC> run )
	{
		return( safeFunctionExecution( run, false ) );
	}

	public <CC> CC safeFunctionExecution( UnsafeFunction<CC> run )
	{
		return( safeFunctionExecution( run, true ) );
	}

	protected <CC> CC safeFunctionExecution( UnsafeFunction<CC> run, boolean traceException )
	{
		CC result = null;
		try
		{
			if( ! Thread.currentThread().isInterrupted() )
				result = run.run();
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		catch( Exception ex )
		{
			if( traceException )
				ex.printStackTrace();
		}

		return( result );
	}

	public <CC> CC runtimeExceptionFunctionExecution( UnsafeFunction<CC> run )
	{
		CC result = null;
		try
		{
			if( ! Thread.currentThread().isInterrupted() )
				result = run.run();
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
			throw( new RuntimeException( ex.getMessage(), ex ) );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
			throw( new RuntimeException( ex.getMessage(), ex ) );
		}

		return( result );
	}

	public Exception runtimeExceptionMethodExecution( UnsafeMethod run )
	{
		Exception result = null;
		try
		{
			if( ! Thread.currentThread().isInterrupted() )
				run.run();
		}
		catch (InterruptedException ex)
		{
			result = ex;
			Thread.currentThread().interrupt();
			throw( new RuntimeException( ex.getMessage(), ex ) );
		}
		catch( Exception ex )
		{
			result = ex;
			ex.printStackTrace();
			throw( new RuntimeException( ex.getMessage(), ex ) );
		}

		return( result );
	}

	public void invokeLaterIfNecessary( Runnable runnable )
	{
		if( SwingUtilities.isEventDispatchThread() )
			runnable.run();
		else
			SwingUtilities.invokeLater( runnable );
	}

	public interface UnsafeMethod
	{
		public void run() throws Exception;
	}

	public interface UnsafeFunction<CC>
	{
		public CC run() throws Exception;
	}
}
