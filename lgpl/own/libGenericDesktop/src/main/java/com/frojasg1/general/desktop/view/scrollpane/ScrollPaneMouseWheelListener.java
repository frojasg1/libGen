/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.scrollpane;

import com.frojasg1.general.desktop.keyboard.IsKeyPressed;
import com.frojasg1.general.desktop.view.ComponentFunctions;
import com.frojasg1.general.number.IntegerFunctions;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ScrollPaneMouseWheelListener implements MouseWheelListener
{
	protected JScrollPane _parent = null;

	protected boolean _scrollByProgram = false;

	public ScrollPaneMouseWheelListener( JScrollPane parent )
	{
		_parent = parent;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt)
	{
		int units = evt.getUnitsToScroll();

		if( IsKeyPressed.isKeyPressed( KeyEvent.VK_CONTROL ) )
		{
		}
		else
		{
			if( _parent != null )
			{
				JScrollBar scrollBar = null;

				if( IsKeyPressed.isKeyPressed( KeyEvent.VK_SHIFT ) )
				{
					scrollBar = _parent.getHorizontalScrollBar();
				}
				else
				{
					scrollBar = _parent.getVerticalScrollBar();
				}

				if (evt.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
				{
					incrementScrollBarValue( scrollBar, ( units * scrollBar.getVisibleAmount() ) / 26 );
				}
				else
				{ //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
					incrementScrollBarValue( scrollBar, scrollBar.getVisibleAmount() / 26 );
				}
			}
		}
	}

	protected void incrementScrollBarValue( JScrollBar scrollBar, int increment )
	{
		setScrollBarValue( scrollBar, scrollBar.getValue() + increment );
	}

	protected void setScrollBarValue( JScrollBar scrollBar, int value )
	{
		int valueToSet = IntegerFunctions.min( scrollBar.getMaximum(),
												IntegerFunctions.max( scrollBar.getMinimum(), value) );

		_scrollByProgram = true;
		scrollBar.setValue(valueToSet);
		_scrollByProgram = false;
	}

	public void addListeners()
	{
		addListeners( _parent );
	}

	public void addListeners(Component component)
	{
		_parent.setWheelScrollingEnabled( false );		// we will program manually the wheel scrolling.
		ComponentFunctions.instance().browseComponentHierarchy(component, comp -> { comp.addMouseWheelListener(this); return null; } );
	}

	public void removeListeners(Component component)
	{
		ComponentFunctions.instance().browseComponentHierarchy(component, comp -> { comp.removeMouseWheelListener(this); return null; } );
	}

	public void removeListeners()
	{
		removeListeners( _parent );
	}

	public void dispose()
	{
		removeListeners();
		_parent = null;
	}
}
