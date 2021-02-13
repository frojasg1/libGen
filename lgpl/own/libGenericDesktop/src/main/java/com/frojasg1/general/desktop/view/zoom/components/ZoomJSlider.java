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
package com.frojasg1.general.desktop.view.zoom.components;

import com.frojasg1.general.desktop.view.zoom.ZoomComponentInterface;
import com.frojasg1.general.number.DoubleReference;
import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomJSlider extends JSlider implements ZoomComponentInterface
{
	protected ComponentForZoomOverriden _overridenMethods = null;
	protected DoubleReference _zoomFactor = new DoubleReference( 1.0D );

    public ZoomJSlider() {
		super();

		setOnChangeRepaint();
	}

	public ZoomJSlider(int orientation) {
        super(orientation);

		setOnChangeRepaint();
    }

    public ZoomJSlider(int min, int max) {
        super(min, max);

		setOnChangeRepaint();
    }

    public ZoomJSlider(int min, int max, int value) {
        super(min, max, value);

		setOnChangeRepaint();
    }

    public ZoomJSlider(int orientation, int min, int max, int value)
    {
        super(orientation, min, max, value);

		setOnChangeRepaint();
    }

    public ZoomJSlider(BoundedRangeModel brm)
    {
		super( brm );

		setOnChangeRepaint();
	}

	protected void setOnChangeRepaint()
	{
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				repaint();
			}
		});
		
	}

	@Override
	public void initBeforeCopyingAttributes()
	{
		if( _overridenMethods == null )
			_overridenMethods = createOverridenMethodsObject();
	}

	protected ComponentForZoomOverriden createOverridenMethodsObject()
	{
		return( new ComponentForZoomOverriden( this, _zoomFactor ) );
	}
	
	@Override
	public void setUI( ComponentUI ui )
	{
		super.setUI( ui );
	}

	@Override
	public void switchToZoomUI()
	{
		_overridenMethods.switchToZoomUI();
	}

	@Override
	public void setZoomFactor( double zoomFactor )
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
	public void setZoomFactorReference(DoubleReference zoomFactor)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DoubleReference getZoomFactorReference() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void initAfterCopyingAttributes()
	{
		
	}
}
