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
package com.frojasg1.general.desktop.view.zoom.imp;

import com.frojasg1.general.desktop.view.zoom.ZoomIcon;
import com.frojasg1.general.number.DoubleReference;
import com.frojasg1.general.number.IntegerFunctions;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomMetalComboBoxIcon extends MetalComboBoxIcon implements ZoomIcon {

	protected double _zoomFactor = 1.0D;

	protected MetalComboBoxButton _parent = null;
	protected int _originalComboBoxHeight = -1;

	public ZoomMetalComboBoxIcon( MetalComboBoxButton parent )
	{
//		_originalComboBoxHeight = parent.getComboBox().getPreferredSize().height;
		_parent = parent;
	}

	@Override
    public void paintIcon(Component c, Graphics g, int x, int y){
        JComponent component = (JComponent)c;
        int iconWidth = getIconWidth();

        g.translate( x, y );

        g.setColor(component.isEnabled()
                   ? MetalLookAndFeel.getControlInfo()
                   : MetalLookAndFeel.getControlShadow());
        g.fillPolygon(new int[]{0, iconWidth/2, iconWidth},
                      new int[]{0, iconWidth/2 , 0}, 3);
        g.translate( -x, -y );
    }

	@Override
	public int getIconWidth()
	{
//		int total = IntegerFunctions.zoomValueFloor(_originalComboBoxHeight, _zoomFactor);
		int total = _parent.getComboBox().getSize().height;

		Insets insets = _parent.getInsets();

		int result = total;

		if( insets != null )
		{
			result = result - insets.left - insets.right;
		}

		return( result );
	}


	@Override
	public int getIconHeight()
	{
		return( getIconWidth() / 2 );
	}

	@Override
	public void setZoomFactorReference(DoubleReference zoomFactor) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setZoomFactor(double zoomFactor) {
		_zoomFactor = zoomFactor;
	}

	@Override
	public double getZoomFactor() {
		return( _zoomFactor );
	}

	@Override
	public DoubleReference getZoomFactorReference() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
