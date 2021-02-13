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

import com.frojasg1.general.desktop.view.zoom.ZoomComponentInterface;
import com.frojasg1.general.desktop.view.zoom.components.ZoomMetalScrollButton_forScrollBar;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.plaf.metal.MetalScrollBarUI;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomMetalScrollBarUI extends MetalScrollBarUI
{
    /** Returns the view that represents the decrease view.
      */
    protected JButton createDecreaseButton( int orientation )
    {
        ZoomMetalScrollButton_forScrollBar db = new ZoomMetalScrollButton_forScrollBar( orientation, scrollBarWidth, isFreeStanding );
		db.initBeforeCopyingAttributes();

		decreaseButton = db;
		
		return decreaseButton;
    }

    /** Returns the view that represents the increase view. */
    protected JButton createIncreaseButton( int orientation )
    {
        ZoomMetalScrollButton_forScrollBar ib = new ZoomMetalScrollButton_forScrollBar( orientation, scrollBarWidth, isFreeStanding );
		ib.initBeforeCopyingAttributes();

		increaseButton =  ib;

		return increaseButton;
    }

	protected void initAfterCopyingAttributes( Component comp )
	{
		if( comp instanceof ZoomComponentInterface )
		{
			ZoomComponentInterface zci = (ZoomComponentInterface) comp;
			zci.initAfterCopyingAttributes();
		}
	}

	public void initAfterCopyingAttributes()
	{
		initAfterCopyingAttributes( incrButton );
		initAfterCopyingAttributes( decrButton );
	}
}
