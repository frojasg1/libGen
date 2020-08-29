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
package com.frojasg1.applications.common.components.data;

import com.frojasg1.applications.common.components.internationalization.InternException;
import com.frojasg1.general.desktop.copypastepopup.TextCompPopupManager;
import com.frojasg1.general.undoredo.text.TextUndoRedoInterface;
import com.frojasg1.applications.common.components.resizecomp.MapResizeRelocateComponentItem;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem_parent;
import java.awt.Component;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class MapOfComponents
{
	protected Map< Component, ComponentData > _map;

	ResizeRelocateItem_parent _rriParent;

	public MapOfComponents(ResizeRelocateItem_parent rriParent)
	{
		_rriParent = rriParent;
		_map = new HashMap< Component, ComponentData >();
	}

	public ComponentData get( Component comp )
	{
		return( _map.get(comp) );
	}

	public void put( Component comp, ComponentData cd )
	{
		_map.put( comp, cd );
	}

	public ComponentData getOrCreate( Component comp )
	{
		ComponentData result = get(comp);
		if( result == null )
		{
			result = new ComponentData();
			put( comp, result );
		}
		return( result );
	}
	
	public ResizeRelocateItem getResizeRelocateItem( Component comp )
	{
		ResizeRelocateItem result = null;
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getResizeRelocateItem();

		return( result );
	}

	public InfoForResizingPanels getInfoForResizingPanels( Component comp )
	{
		InfoForResizingPanels result = null;
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getInfoForResizingPanels();

		return( result );
	}

	public boolean getResizeParents( Component comp )
	{
		boolean result = false;
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getResizeParents();

		return( result );
	}

	public TextUndoRedoInterface getTextUndoRedoManager( Component comp )
	{
		TextUndoRedoInterface result = null;
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getTextUndoRedoManager();

		return( result );
	}

	public TextCompPopupManager getTextCompPopupManager( Component comp )
	{
		TextCompPopupManager result = null;
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getTextCompPopupManager();

		return( result );
	}

	public void setResizeRelocateItem( Component comp, ResizeRelocateItem rri )
	{
		ComponentData cd = getOrCreate( comp );
		cd.setResizeRelocateItem( rri );
	}

	public void setInfoForResizingPanels( Component comp, InfoForResizingPanels ifrp )
	{
		ComponentData cd = getOrCreate( comp );
		cd.setInfoForResizingPanels( ifrp );
	}

	public void setResizeParents( Component comp, boolean resizeParents )
	{
		ComponentData cd = getOrCreate( comp );
		cd.setResizeParents( resizeParents );
	}

	public void setTextCompPopupManager( Component comp, TextCompPopupManager tpmm )
	{
		ComponentData cd = getOrCreate( comp );
		cd.setTextCompPopupManager(tpmm);
	}

	public Iterator<Map.Entry<Component, ComponentData>> getEntrySetIterator()
	{
		return( _map.entrySet().iterator() );
	}

	public void removeResizeRelocateComponentItem( Component comp )
	{
		ComponentData cd = get( comp );
		if( cd != null )
		{
			cd.setResizeRelocateItem( null );
			if( cd.isEmpty() )
				_map.remove( comp );
		}
	}

	public void addMapResizeRelocateComponents( MapResizeRelocateComponentItem map )
	{
		addMapResizeRelocateComponents( map, 1.0D );
	}

	public void addMapResizeRelocateComponents( MapResizeRelocateComponentItem map,
												double zoomFactor )
	{
		Iterator<Map.Entry< Component, ResizeRelocateItem >> it = map.entrySet().iterator();
		
		while( it.hasNext() )
		{
			Map.Entry< Component, ResizeRelocateItem > entry = it.next();

			Component comp = entry.getKey();
			Component newComp = map.switchComponent(comp);

			ResizeRelocateItem rri = entry.getValue();

			if( newComp != comp )
			{
				rri.setComponent( newComp );
			}

			try
			{
				if( !rri.isInitialized() )
					rri.initialize( zoomFactor );
			}
			catch( Throwable th )
			{
				th.printStackTrace();
			}

			setResizeRelocateItem( newComp, rri );
		}
	}

	protected void switchComponent( Component oldComp, Component newComp )
	{
		ComponentData cd = get( oldComp );
		if( cd != null )
		{
			ResizeRelocateItem rri = cd.getResizeRelocateItem();

			_map.remove( oldComp );
			_map.put( newComp, cd );
			if( rri != null )
				rri.setComponent(newComp);
		}
	}

	public void switchComponents( Map< Component, Component > switchMap )
	{
		Iterator< Map.Entry< Component, Component > > it = switchMap.entrySet().iterator();
		while( it.hasNext() )
		{
			Map.Entry< Component, Component > entry = it.next();

			if( entry.getValue() == null )
				this.removeResizeRelocateComponentItem( entry.getValue() );
			else
			{
				switchComponent( entry.getKey(), entry.getValue() );
				ResizeRelocateItem rri = getResizeRelocateItem( entry.getKey() );
				if( rri != null )
					rri.setComponent( entry.getValue() );
			}
		}
	}

	protected boolean hasToCreateResizeRelocateItem( Component comp )
	{
		boolean result = false;

		result = !( comp instanceof BasicSplitPaneDivider );

		return( result );
	}

	public void createAndStoreNewResizeRelocateItem( Component comp ) throws InternException
	{
		if( hasToCreateResizeRelocateItem( comp ) )
		{
			ResizeRelocateItem rri = createDefaultResizeRelocateItem( comp );

			setResizeRelocateItem(comp, rri);
		}
	}

	public ResizeRelocateItem createDefaultResizeRelocateItem( Component comp ) throws InternException
	{
		int flags = 0;
		boolean postpone_initialization = false;

		boolean isAlreadyZoomed = false;
		ResizeRelocateItem rri = ResizeRelocateItem.buildResizeRelocateItem(comp, flags,
													_rriParent, postpone_initialization,
													isAlreadyZoomed );

		return( rri );
	}

	public void createComponents( Collection<Component> col )
	{
		Iterator< Component > it = col.iterator();
		while( it.hasNext() )
		{
			try
			{
				createAndStoreNewResizeRelocateItem( it.next() );
			}
			catch( Exception ex )
			{}
		}
	}
/*
	public int getFactoredTextSize( Component comp, double factor )
	{
		int result = -1;
		
		ComponentData cd = get( comp );
		if( cd != null )
			result = cd.getFactoredTextSize( factor );

		return( result );
	}

	public void setOriginalTextSize( Component comp, double originalTextSize )
	{
		ComponentData cd = getOrCreate( comp );
		cd.setOriginalTextSize( originalTextSize );
	}
*/
}
