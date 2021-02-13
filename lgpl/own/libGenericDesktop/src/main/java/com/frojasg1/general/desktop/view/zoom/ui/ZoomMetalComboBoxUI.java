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
package com.frojasg1.general.desktop.view.zoom.ui;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomMetalComboBoxUI extends MetalComboBoxUI {

    protected ComboPopup createPopup() {
        return new BasicComboPopup( comboBox ) {
			
			@Override
			public void setSize( int width, int height )
			{
				super.setSize( width, height );
			}

			@Override
			public void setSize( Dimension newSize )
			{
				super.setSize( newSize );
			}

			@Override
			public void setBounds( Rectangle newBounds )
			{
				super.setBounds( newBounds );
			}

			@Override
			public void setBounds( int xx, int yy, int width, int height )
			{
				super.setBounds( xx, yy, width, height );
			}

			@Override
			public void setPreferredSize( Dimension dimen )
			{
				super.setPreferredSize( dimen );
			}
		};
    }
}
