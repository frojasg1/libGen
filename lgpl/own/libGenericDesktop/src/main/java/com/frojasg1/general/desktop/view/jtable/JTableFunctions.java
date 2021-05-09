/* 
 * Copyright (C) 2021 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
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
package com.frojasg1.general.desktop.view.jtable;

import com.frojasg1.general.desktop.view.combobox.utils.*;
import com.frojasg1.general.ArrayFunctions;
import com.frojasg1.general.desktop.view.combobox.JComboBoxContainer;
import com.frojasg1.general.desktop.view.zoom.mapper.ComponentMapper;
import com.frojasg1.general.desktop.view.zoom.mapper.InternallyMappedComponent;
import com.frojasg1.general.number.IntegerFunctions;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class JTableFunctions
{
	protected static JTableFunctions _instance;

	public static void changeInstance( JTableFunctions instance )
	{
		_instance = instance;
	}

	public static JTableFunctions instance()
	{
		if( _instance == null )
			_instance = new JTableFunctions();
		
		return( _instance );
	}

	public TableCellRenderer getTableCellRenderer( JTable table, int column )
	{
		TableCellRenderer result = null;

		if( ( table.getColumnModel() != null ) &&
			( table.getColumnModel().getColumnCount() > column ) )
		{
			result = table.getCellRenderer(0, column);
		}

		return( result );
	}

	public void zoomCellRenderer(TableCellRenderer tcr,	double zoomFactor )
	{
		if( tcr instanceof Component )
		{
			zoomCellRenderer( tcr, ((Component) tcr).getPreferredSize().height,
								zoomFactor );
		}
	}

	protected void zoomCellRenderer(TableCellRenderer tcr, Integer originalCellPreferredHeight,
								double zoomFactor )
	{
		if( ( tcr instanceof Component ) && ( originalCellPreferredHeight != null ) )
		{
			Component tcrComp = (Component) tcr;

			tcrComp.setPreferredSize( new Dimension( tcrComp.getPreferredSize().width,
				IntegerFunctions.zoomValueCeil(originalCellPreferredHeight, zoomFactor ) ) );
		}
	}

	public void zoomTableRows(JTable jTable, double zoomFactor )
	{
		zoomCellRenderer(getTableCellRenderer( jTable, 0 ),	zoomFactor );
		zoomTableRowHeight( jTable, jTable.getRowHeight(), zoomFactor );
	}

	public void zoomTableRows(JTable jTable, Integer originalCellPreferredHeight,
								Integer originalRowHeightForTable, double zoomFactor )
	{
		if( originalCellPreferredHeight != null )
		{
			zoomCellRenderer(getTableCellRenderer( jTable, 0 ),
				originalCellPreferredHeight, zoomFactor );
		}

		zoomTableRowHeight( jTable, originalRowHeightForTable, zoomFactor );
	}

	public void zoomTableRowHeight(JTable jTable, double zoomFactor )
	{
		zoomTableRowHeight(jTable, jTable.getRowHeight(), zoomFactor );
	}

	protected void zoomTableRowHeight(JTable jTable,
								Integer originalRowHeightForTable,
								double zoomFactor )
	{
		double newHeight = Math.round( originalRowHeightForTable * zoomFactor );
		jTable.setRowHeight( ( new Double( newHeight ) ).intValue() );
	}
}
