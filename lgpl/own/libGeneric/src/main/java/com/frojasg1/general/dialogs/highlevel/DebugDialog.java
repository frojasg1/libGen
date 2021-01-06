/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.dialogs.highlevel;

import com.frojasg1.general.clipboard.SystemClipboard;
import com.frojasg1.general.view.ViewComponent;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class DebugDialog {

	protected static DebugDialog _instance = null;

	protected boolean _debug = false;

	protected StringBuilder _sb = new StringBuilder();

	protected ReentrantLock _lock = new ReentrantLock( true );

	public static DebugDialog instance()
	{
		if( _instance == null )
			_instance = new DebugDialog();

		return( _instance );
	}

	protected DebugDialog()
	{
	}

	public Integer yesNoCancelDialogAlways( ViewComponent parent, Object message, String title,
									Integer initialValue )
	{
		_sb.setLength(0);
		return( yesNoCancelDialogAlways_internal( parent, message, title, initialValue ) );
	}

	public Integer yesNoCancelDialogAlways_internal( ViewComponent parent, Object message, String title,
									Integer initialValue )
	{
		log( message );
		SystemClipboard.instance().setClipboardContents( _sb.toString() );

		Integer result = HighLevelDialogs.instance().yesNoCancelDialog(null, message, title, initialValue );

		if( Objects.equals(result, HighLevelDialogs.YES) )
			_debug = true;
		else
			_debug = false;

		return( result );
	}

	protected String toString( Object obj )
	{
		String result = "null";
		if( obj != null )
			result = obj.toString();

		return( result );
	}

	public void log( Object obj )
	{
		try
		{
			_lock.lock();

			_sb.append( toString( obj ) ).append( "\n" );
		}
		finally
		{
			_lock.unlock();
		}
	}

	public Integer yesNoCancelDialog( ViewComponent parent, Object message, String title,
									Integer initialValue )
	{
		Integer result = null;

		if( _debug )
			result = yesNoCancelDialogAlways_internal(null, message, title, initialValue );

		return( result );
	}
}
