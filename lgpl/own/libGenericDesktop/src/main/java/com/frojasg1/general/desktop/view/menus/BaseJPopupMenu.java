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
package com.frojasg1.general.desktop.view.menus;

import com.frojasg1.general.desktop.view.ComponentFunctions;
import com.frojasg1.general.desktop.view.ViewFunctions;
import com.frojasg1.general.desktop.view.zoom.mapper.InternallyMappedComponent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 *
 * @author Usuario
 */
public abstract class BaseJPopupMenu extends JPopupMenu implements ActionListener,
																	MouseListener,
																	InternallyMappedComponent
{
	protected Component _component = null;
	protected Component _ancestor = null;

	public BaseJPopupMenu( Component comp )
	{
		_component = comp;
	}

	public Component getAncestor()
	{
		if( ( _ancestor == null ) && ( _component != null ) )
			_ancestor = ComponentFunctions.instance().getAncestor(_component);

		return( _ancestor );
	}

	public void addMouseListenerToAllComponents()
	{
		recursiveAddMouseListener( this );
	}

	protected void empty()
	{
		removeMouseListenerToAllComponents();

		while( getComponentCount() > 0 )
			this.remove( 0 );
	}

	protected void recursiveAddMouseListener( Component comp )
	{
		comp.addMouseListener( this );
		if( comp instanceof Container )
		{
			Container cont = (Container) comp;
			
			for( int ii=0; ii<cont.getComponentCount(); ii++ )
			{
				recursiveAddMouseListener( cont.getComponent(ii) );
			}
		}
	}
	
	public void removeMouseListenerToAllComponents()
	{
		recursiveRemoveMouseListener( this );
	}

	protected void recursiveRemoveMouseListener( Component comp )
	{
		comp.removeMouseListener( this );
		if( comp instanceof Container )
		{
			Container cont = (Container) comp;
			
			for( int ii=0; ii<cont.getComponentCount(); ii++ )
			{
				recursiveRemoveMouseListener( cont.getComponent(ii) );
			}
		}
	}

	protected void addMenuComponent( Component comp )
	{
		add( comp );
		if( comp instanceof JMenuItem )
		{
			JMenuItem mi = (JMenuItem) comp;
			mi.addActionListener(this);
		}
	}

	protected void addMenuComponent( JSeparator sep )
	{
		add( sep );
	}

	@Override
	public void actionPerformed( ActionEvent evt )
	{
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
	}

	@Override
	public void mousePressed(MouseEvent me)
	{
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
	}

	@Override
	public void mouseEntered(MouseEvent me)
	{
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
		Point mouseLocation = me.getLocationOnScreen();
		Point leftTopCorner = getLocationOnScreen();
		Point rightBottomCorner = new Point( (int) (leftTopCorner.getX() + getWidth()),
											(int) (leftTopCorner.getY() + getHeight()) );

		if( ( mouseLocation.getX() <= leftTopCorner.getX() ) ||
			( mouseLocation.getX() >= rightBottomCorner.getX() ) ||
			( mouseLocation.getY() <= leftTopCorner.getY() ) ||
			( mouseLocation.getY() >= rightBottomCorner.getY() ) )
		{
			setVisible(false);
		}
	}

	protected abstract void preparePopupMenuItems();

	public void doPopup( MouseEvent evt )
	{
//		createMenuIfAnyChange();

		preparePopupMenuItems();

		Point position = new Point( evt.getX() - 10, evt.getY() - 10 );
		position = ViewFunctions.instance().getValidPositionToPlaceComponent( this, position );
		show(evt.getComponent(), evt.getX() - 10, evt.getY() - 10);
	}

	@Override
	public void setVisible( boolean value )
	{
		super.setVisible(value);

		if( !value && ( getAncestor() != null ) )
			getAncestor().repaint();
	}
}
