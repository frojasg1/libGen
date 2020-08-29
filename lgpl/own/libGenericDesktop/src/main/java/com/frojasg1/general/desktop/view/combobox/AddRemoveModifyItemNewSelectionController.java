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
package com.frojasg1.general.desktop.view.combobox;

import com.frojasg1.general.desktop.view.combobox.chained.ComboBoxGroupManager;
import com.frojasg1.general.desktop.view.combobox.chained.ComboBoxSelectionChangedListener;
import com.frojasg1.general.desktop.view.zoom.mapper.InternallyMappedComponent;

/**
 *
 * @author fjavier.rojas
 */
public interface AddRemoveModifyItemNewSelectionController extends ComboBoxSelectionChangedListener,
																	InternallyMappedComponent {

	public String added( ComboBoxGroupManager sender, AddRemoveModifyItemResult eventData );
	public String removed( ComboBoxGroupManager sender, AddRemoveModifyItemResult eventData );
	public void modify( ComboBoxGroupManager sender, AddRemoveModifyItemResult eventData );
}
