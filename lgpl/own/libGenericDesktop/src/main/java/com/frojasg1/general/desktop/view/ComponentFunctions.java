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
package com.frojasg1.general.desktop.view;

import com.frojasg1.general.view.ViewComponent;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ComponentFunctions
{
	protected static ComponentFunctions _instance;

	public static void changeInstance( ComponentFunctions inst )
	{
		_instance = inst;
	}

	public static ComponentFunctions instance()
	{
		if( _instance == null )
			_instance = new ComponentFunctions();
		return( _instance );
	}

	public void releaseResources( Component comp )
	{
		if( comp instanceof ViewComponent )
			( (ViewComponent) comp ).releaseResources();
	}

	public void browseComponentHierarchy( Component comp, ExecuteToComponent executeToComp )
	{
		if( comp != null )
		{
			if( executeToComp != null )
			{
				Component relatedComponent = executeToComp.executeToComponent(comp);
				if( relatedComponent != null )
					browseComponentHierarchy( relatedComponent, executeToComp );
			}

			if( ( comp instanceof JFrame ) ||
				( comp instanceof JRootPane ) ||
				( comp instanceof JLayeredPane ) ||
				( comp instanceof JPanel ) ||
				( comp instanceof JInternalFrame )  ||
				( comp instanceof Container ) )
			{
				if( comp instanceof JSplitPane	)
				{
					JSplitPane jsp = (JSplitPane) comp;
					if( jsp.getOrientation() == JSplitPane.HORIZONTAL_SPLIT )
					{
						browseComponentHierarchy( jsp.getLeftComponent(), executeToComp );
						browseComponentHierarchy( jsp.getRightComponent(), executeToComp );
					}
					else if( jsp.getOrientation() == JSplitPane.VERTICAL_SPLIT )
					{
						browseComponentHierarchy( jsp.getTopComponent(), executeToComp );
						browseComponentHierarchy( jsp.getBottomComponent(), executeToComp );
					}
				}
				else if( comp instanceof JScrollPane )
				{
					JScrollPane sp = (JScrollPane) comp;
					browseComponentHierarchy( sp.getViewport(), executeToComp );
				}
				else if( comp instanceof JViewport )
				{
					JViewport vp = (JViewport) comp;
					browseComponentHierarchy( vp.getView(), executeToComp );
				}

				if( comp instanceof JTabbedPane )
				{
					JTabbedPane tabbedPane = (JTabbedPane) comp;
					for( int ii=0; ii<tabbedPane.getTabCount(); ii++ )
					{
						browseComponentHierarchy( tabbedPane.getComponentAt(ii), executeToComp );
					}
				}
				else if( comp instanceof JComboBox )
				{
					JComboBox combo = (JComboBox) comp;
					BasicComboPopup popup = (BasicComboPopup) combo.getUI().getAccessibleChild(combo, 0);

					browseComponentHierarchy( popup, executeToComp );
				}
				else if( comp instanceof Container )
				{
					Container contnr = (Container) comp;

					if( comp instanceof JMenu )
						browseComponentHierarchy( ( ( JMenu ) comp ).getPopupMenu(), executeToComp );

					for( int ii=0; ii<contnr.getComponentCount(); ii++ )
					{
						browseComponentHierarchy( contnr.getComponent(ii), executeToComp );
					}
				}
			}
		}
	}

	public String getComponentString( Component comp )
	{
		String result = "null";
		if( comp != null )
		{
			result = String.format( "%s name=%s", comp.getClass().getName(), comp.getName() );
		}

		return( result );
	}

	public boolean isVisible( Component comp )
	{
		boolean isVisible = true;
		
		Component current = comp;
		while( isVisible && ( current != null ) )
		{
			isVisible = current.isVisible();

			current = current.getParent();
		}

		return( isVisible );
	}

	public Component getAncestor( Component comp )
	{
		Component result = null;
		if( comp != null )
			result = SwingUtilities.getRoot( comp );

		return( result );
	}

	public boolean isAnyParentInstanceOf( Class<?> clazz, Component comp )
	{
		boolean result = false;
		
		if( ( comp != null ) && ( clazz != null ) )
		{
			Component current = comp;
			while( ( current != null ) && !result )
			{
				result = clazz.isInstance(comp);
				current = current.getParent();
			}
		}

		return( result );
	}

	public boolean isAnyParent( Component possibleParent, Component comp )
	{
		boolean result = false;
		
		if( ( possibleParent != null ) && ( comp != null ) )
		{
			Component current = comp.getParent();
			while( ( current != null ) && !result )
			{
				result = ( possibleParent == current );
				current = current.getParent();
			}
		}

		return( result );
	}

	public boolean isViewportView( Component component )
	{
		boolean result = false;
		JScrollPane jsp = getScrollPane( component );
		if( jsp != null )
			result = jsp.getViewport().getView() == component;

		return( result );
	}

	public JScrollPane getScrollPane( Component component )
	{
		ComponentFunctions cf;
		JScrollPane result = null;
		if( component != null )
		{
			Component parent = component.getParent();
			if( parent instanceof JViewport )
			{
				parent = parent.getParent();
				if( parent instanceof JScrollPane )
					result = (JScrollPane) parent;
			}
			else if( parent instanceof JScrollPane )
			{
				result = (JScrollPane) parent;
			}
		}

		return( result );
	}

	public Component getFocusedComponent()
	{
		return( KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() );
	}

	public boolean isComponentListenerAdded( Component comp, ComponentListener listener )
	{
		return( isGenListenerAdded( comp, listener, (com) -> com.getComponentListeners() ) );
	}

	public boolean isMouseListenerAdded( Component comp, MouseListener listener )
	{
		return( isGenListenerAdded( comp, listener, (com) -> com.getMouseListeners() ) );
	}

	public <CC, LL> boolean isGenListenerAdded( CC comp, LL listener, Function<CC, LL[]> getter )
	{
		boolean result = false;
		if( ( comp != null ) && ( getter != null ) )
		{
			LL[] arr = getter.apply(comp);
			result = Arrays.stream( arr ).anyMatch( (lsnr) -> listener==lsnr );
		}

		return( result );
	}

	protected String getComponentName( Component comp )
	{
		String result = ViewFunctions.instance().instance().getComponentName(comp);

		return( result );
	}

	public boolean selectJTabbedPaneIndex( JTabbedPane tp, String panelName )
	{
		boolean result = false;

		if( tp != null )
		{
			for( int ii=0; ii<tp.getTabCount(); ii++ )
			{
				Component tab = tp.getComponentAt( ii );
				String name = getComponentName( tab );
				if( Objects.equals( name, panelName ) )
				{
					result = true;
					tp.setSelectedComponent(tab);
				}
			}
		}

		return( result );
	}

	public interface ExecuteToComponent
	{
		public Component executeToComponent( Component comp );
	}
}
