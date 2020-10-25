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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.metal.MetalScrollButton;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ScrollPaneMouseListener extends MouseAdapter
{
	protected JScrollPane _parent = null;

	protected boolean _scrollByProgram = false;

	public ScrollPaneMouseListener( JScrollPane parent )
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

	@Override
	public void mousePressed(MouseEvent evt)
	{
		// for vertical scroll
		if( (evt.getButton() == MouseEvent.BUTTON1 ) && ( evt.getComponent() instanceof MetalScrollButton ) && ( _parent != null ) )
		{
			int units = getUnitsFromComponentClicked( evt.getComponent() );

			JScrollBar scrollBar = _parent.getVerticalScrollBar();
			incrementScrollBarValue( scrollBar, ( units * scrollBar.getVisibleAmount() ) / 26 );
		}
	}

	/**
	 * 
	 * @param component		It will be a MetalScrollButton of the Vertical Scroll Bar.
	 * @return				1 - Up button clicked
	 *						-1 - Down button clicked
	 *						0 - Otherwise
	 */
	protected int getUnitsFromComponentClicked( Component component )
	{
		int result = 0;

		if( _parent != null )
		{
			if( component == _parent.getVerticalScrollBar().getComponent( 0 ) )		// up button
				result = 1;
			else if( component == _parent.getVerticalScrollBar().getComponent( 1 ) )		// down button
				result = -1;
		}

		return( result );
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
		if( component instanceof JScrollPane )
		{
			JScrollPane jsp = (JScrollPane) component;

			jsp.setWheelScrollingEnabled( false );		// we will program manually the wheel scrolling.
			jsp.getVerticalScrollBar().getComponent(0).addMouseListener(this);
			jsp.getVerticalScrollBar().getComponent(1).addMouseListener(this);
		}

		ComponentFunctions.instance().browseComponentHierarchy(component, comp -> { comp.addMouseWheelListener(this); return null; } );
	}

	public void removeListeners(Component component)
	{
		ComponentFunctions.instance().browseComponentHierarchy(component, comp -> { comp.removeMouseWheelListener(this); return null; } );

		if( component instanceof JScrollPane )
		{
			JScrollPane jsp = (JScrollPane) component;

			jsp.getVerticalScrollBar().getComponent(0).removeMouseListener(this);
			jsp.getVerticalScrollBar().getComponent(1).removeMouseListener(this);
		}
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
