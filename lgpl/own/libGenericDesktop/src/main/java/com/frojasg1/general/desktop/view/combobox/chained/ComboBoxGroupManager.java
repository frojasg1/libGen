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
package com.frojasg1.general.desktop.view.combobox.chained;

import com.frojasg1.general.desktop.view.combobox.AddRemoveModifyItemNewSelectionController;
import java.awt.Component;
import java.util.List;
import com.frojasg1.general.combohistory.TextComboBoxContent;
import com.frojasg1.general.desktop.view.zoom.mapper.InternallyMappedComponent;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface ComboBoxGroupManager extends AddRemoveModifyItemNewSelectionController, InternallyMappedComponent
{
	public void setListOfItems( List<String> list );
	public void setComboBoxContent( TextComboBoxContent content );
	public TextComboBoxContent getComboBoxContent();

	public void setController( AddRemoveModifyItemNewSelectionController controller );

	// Component must be either a JComboBox or a JComboBoxContainer
	public List<Component> getComboCompList();
	public void addComboComp( Component comboComp );
	public boolean removeComboComp( Component comboComp );

	public String getSelectedItem();
	public void saveCurrentItem();

	public void updateCombos();
	public void updateCombosKeepingSelection();

	public void dispose();

//	public void addListeners();
}
