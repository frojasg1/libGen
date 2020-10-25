/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.applications.common.components.internationalization;

import com.frojasg1.general.ExecutionFunctions;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ExtendedZoomSemaphore
{
	protected Semaphore _semaphore;
	protected int _multiplier = 1;
	protected boolean _activated = false;
	protected int _count = 0;

	public void init()
	{
		_semaphore = new Semaphore(0);
	}

	public void setMultiplier( int value )
	{
		_multiplier = value;
	}

	public int getMultiplier()
	{
		return( _multiplier );
	}

	public void setActivated( boolean value )
	{
		_activated = value;
	}

	public boolean isActivated()
	{
		return( _activated );
	}

	public Semaphore getSemaphore()
	{
		return( _semaphore );
	}

	public void increaseCount()
	{
		_count++;
	}

	public void setCount( int count )
	{
		_count = count;
	}

	public void tryAcquire( int ms )
	{
		ExecutionFunctions.instance().safeMethodExecution( () -> _semaphore.tryAcquire(getTotalNumPermits(), ms, TimeUnit.MILLISECONDS ) );
	}

	protected int getTotalNumPermits()
	{
		return( getNumPermits( _count ) );
	}

	protected int getNumPermits( int elems )
	{
		return( elems * _multiplier );
	}

	public void skipRelease( int skippedElements )
	{
		getSemaphore().release( getNumPermits( skippedElements ) );
	}
}
