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
package com.frojasg1.general.executor.worker;

import com.frojasg1.general.executor.ExecutorInterface;
import com.frojasg1.general.executor.ExecutorPullInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class PullOfExecutorWorkers implements ExecutorPullInterface
{
	protected ArrayList<ExecutorWorker> _listOfWorkers = new ArrayList<ExecutorWorker>();

	protected volatile int _capacity = -1;

	protected ReentrantLock _lockForExecutorToBeProcessed = new ReentrantLock();
	protected Condition _waitForNewExecutorsBeingAvailable = _lockForExecutorToBeProcessed.newCondition();

	protected LinkedList<ExecutorInterface> _listOfExecutorsPendingToBeProcessed = new LinkedList<ExecutorInterface>();

	protected volatile boolean _hasEnded = false;

	protected volatile boolean _isPaused = false;

	protected volatile boolean _isActivated = true;

	public PullOfExecutorWorkers( )
	{
	}

	public void init( int capacity )
	{
		setNumberOfThreads( capacity );
	}

	public boolean isPaused()
	{
		return( _isPaused );
	}

	public void setIsPaused( boolean value )
	{
		try
		{
			_lockForExecutorToBeProcessed.lock();

			_isPaused = value;
			if( !_isPaused && _lockForExecutorToBeProcessed.hasWaiters( _waitForNewExecutorsBeingAvailable ) )
				_waitForNewExecutorsBeingAvailable.signal();
		}
		finally
		{
			_lockForExecutorToBeProcessed.unlock();
		}
	}

	public boolean isActivated()
	{
		return( _isActivated );
	}

	public void setIsActivated( boolean value )
	{
		_isActivated = value;

		if( ! _isActivated )
			clearListOfPendingTasks();
	}

	public void clearListOfPendingTasks()
	{
		try
		{
			_lockForExecutorToBeProcessed.lock();

			_listOfExecutorsPendingToBeProcessed.clear();
		}
		finally
		{
			_lockForExecutorToBeProcessed.unlock();
		}
	}

	protected ExecutorWorker createExecutor()
	{
		ExecutorWorker result = new ExecutorWorker( this );
		return( result );
	}

	public void setNumberOfThreads( int capacity )
	{
		_capacity = capacity;

		if( _capacity < _listOfWorkers.size() )
		{
			List<ExecutorWorker> list = _listOfWorkers.stream().limit( _capacity - _listOfWorkers.size() )
				.collect( Collectors.toList() );

			for( ExecutorWorker worker: list )
			{
				worker.hasToStop();
				_listOfWorkers.remove(worker);
			}
		}
		else
		{
			while( _listOfWorkers.size() < _capacity )
				_listOfWorkers.add( createExecutor() );
		}
	}

	@Override
	public ExecutorInterface getNextExecutor()
	{
		try
		{
			_lockForExecutorToBeProcessed.lock();

			ExecutorInterface result = null;

			while( isPaused() || ( ! getHasEnded() && _listOfExecutorsPendingToBeProcessed.isEmpty() ) )
			{
				try
				{
					_waitForNewExecutorsBeingAvailable.awaitNanos(1000000000L);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}

				if( getHasEnded() )
					break;
			}
			if( ! getHasEnded() )
				result = _listOfExecutorsPendingToBeProcessed.removeLast();

			return( result );
		}
		finally
		{
			_lockForExecutorToBeProcessed.unlock();
		}
	}

	@Override
	public void addPendingNonStopableExecutor( Runnable executor )
	{
		addPendingExecutor( new ExecutorInterface() {
			@Override
			public void hasToStop() {}
			
			@Override
			public void execute()
			{
				executor.run();
			}
		});
	}

	@Override
	public void addPendingExecutor( ExecutorInterface executor )
	{
		if( !isActivated() )
			return;

		try
		{
			_lockForExecutorToBeProcessed.lock();

			_listOfExecutorsPendingToBeProcessed.addFirst(executor);

			if( _lockForExecutorToBeProcessed.hasWaiters( _waitForNewExecutorsBeingAvailable ) )
				_waitForNewExecutorsBeingAvailable.signal();
		}
		finally
		{
			_lockForExecutorToBeProcessed.unlock();
		}
	}

	public boolean getHasEnded()
	{
		return( _hasEnded );
	}

	@Override
	public boolean isFinished()
	{
		boolean result = true;

		Iterator<ExecutorWorker> it = _listOfWorkers.iterator();

		while( result && it.hasNext() )
			result = !it.next().isAlive();

		return( result );
	}

	public void setHasToEnd()
	{
		try
		{
			_lockForExecutorToBeProcessed.lock();
//			_hasEnded = true;

			while( _lockForExecutorToBeProcessed.hasWaiters( _waitForNewExecutorsBeingAvailable ) )
				_waitForNewExecutorsBeingAvailable.signal();
		}
		finally
		{
			_lockForExecutorToBeProcessed.unlock();
		}
	}

	@Override
	public void hasToStop()
	{
		Iterator<ExecutorWorker> it = _listOfWorkers.iterator();

		_hasEnded = true;

		while( it.hasNext() )
			it.next().hasToStop();

		setHasToEnd();
	}

	@Override
	public void start()
	{
		Iterator<ExecutorWorker> it = _listOfWorkers.iterator();

		while( it.hasNext() )
		{
			ExecutorWorker worker = it.next();
			if( !worker.isAlive() )
				worker.start();
		}
	}

	public void cancelThreadsNotStopped()
	{
		Iterator<ExecutorWorker> it = _listOfWorkers.iterator();
		
		while( it.hasNext() )
		{
			ExecutorWorker worker = it.next();
			if( worker.isAlive() )
				worker.stop();
		}
	}
}
