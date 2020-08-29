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
package com.frojasg1.general.desktop.view.zoom.components;

import com.frojasg1.general.desktop.view.ViewFunctions;
import com.frojasg1.general.desktop.view.zoom.ZoomComponentInterface;
import com.frojasg1.general.number.DoubleReference;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomBasicArrowButton_  extends BasicArrowButton implements ZoomComponentInterface
{
	protected DoubleReference _zoomFactor = new DoubleReference( 1.0D );

	public ZoomBasicArrowButton_ ()
	{
		this(BasicArrowButton.NORTH);
	}

	public ZoomBasicArrowButton_ (int direction)
	{
		super(direction);
	}

	public ZoomBasicArrowButton_(int direction, Color background, Color shadow,
							Color darkShadow, Color highlight)
	{
		super(direction, background, shadow, darkShadow, highlight);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return ViewFunctions.instance().getNewDimension( super.getPreferredSize(), _zoomFactor._value );
	}

	@Override
	public Dimension getMinimumSize()
	{
		return ViewFunctions.instance().getNewDimension( super.getMinimumSize(), _zoomFactor._value );
	}

	@Override
	public void setUI( ComponentUI ui )
	{
		super.setUI( ui );
	}

	@Override
	public void initBeforeCopyingAttributes()
	{
	}

	@Override
	public void initAfterCopyingAttributes()
	{
	}

	@Override
	public void switchToZoomUI()
	{
	}

	@Override
	public void setZoomFactorReference(DoubleReference zoomFactor)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setZoomFactor(double zoomFactor)
	{
		_zoomFactor._value = zoomFactor;

		repaint();
	}

	@Override
	public double getZoomFactor()
	{
		return( _zoomFactor._value );
	}

	@Override
	public DoubleReference getZoomFactorReference()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
