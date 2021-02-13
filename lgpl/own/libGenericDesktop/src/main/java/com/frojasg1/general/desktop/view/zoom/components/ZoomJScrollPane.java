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

import com.frojasg1.general.desktop.view.zoom.ResizeSizeComponent;
import com.frojasg1.general.desktop.view.zoom.ZoomComponentInterface;
import com.frojasg1.general.desktop.view.zoom.ZoomFunctions;
import com.frojasg1.general.desktop.view.zoom.ui.ZoomMetalScrollBarUI;
import com.frojasg1.general.number.DoubleReference;
import com.frojasg1.general.number.IntegerFunctions;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.metal.MetalScrollPaneUI;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomJScrollPane extends JScrollPane implements ZoomComponentInterface
{
	protected DoubleReference _zoomFactor = new DoubleReference( 1.0D );

	protected int _originalHorizontalPreferredScrollBarHeight = -1;
	protected int _originalVerticalPreferredScrollBarWidth = -1;

    /**
     * Creates a <code>JScrollPane</code> that displays the view
     * component in a viewport
     * whose view position can be controlled with a pair of scrollbars.
     * The scrollbar policies specify when the scrollbars are displayed,
     * For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code>
     * then the vertical scrollbar only appears if the view doesn't fit
     * vertically. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @see #setViewportView
     *
     * @param view the component to display in the scrollpanes viewport
     * @param vsbPolicy an integer that specifies the vertical
     *          scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *          scrollbar policy
     */
    public ZoomJScrollPane(Component view, int vsbPolicy, int hsbPolicy)
    {
		super( view, vsbPolicy, hsbPolicy );
    }


    /**
     * Creates a <code>JScrollPane</code> that displays the
     * contents of the specified
     * component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     *
     * @see #setViewportView
     * @param view the component to display in the scrollpane's viewport
     */
    public ZoomJScrollPane(Component view) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * with specified
     * scrollbar policies. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * @see #setViewportView
     *
     * @param vsbPolicy an integer that specifies the vertical
     *          scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *          scrollbar policy
     */
    public ZoomJScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * where both horizontal and vertical scrollbars appear when needed.
     */
    public ZoomJScrollPane() {
        this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

	@Override
	public void setZoomFactor( double zoomFactor )
	{
		try
		{
			JScrollBar hsb = getHorizontalScrollBar( );
			hsb = zoomScrollBar( hsb, zoomFactor );
			if( hsb != null )
				setHorizontalScrollBar( hsb );

			MetalScrollPaneUI m;

			JScrollBar vsb = getVerticalScrollBar( );
			vsb = zoomScrollBar( vsb, zoomFactor );
			if( vsb != null )
				setVerticalScrollBar( vsb );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		_zoomFactor._value = zoomFactor;

		for( int ii=0; ii<getComponentCount(); ii++ )
			if( getComponent(ii) == null )
				System.out.println( "null Component" );

		repaint();
	}

	protected JScrollBar zoomScrollBar( JScrollBar sb, double zoomFactor )
	{
		JScrollBar result = null;

		if( sb != null )
		{
			if( zoomFactor != _zoomFactor._value )
			{
				if( sb.getOrientation() == JScrollBar.HORIZONTAL )
					result = zoomHorizontalScrollBar( sb, zoomFactor );
				else if( sb.getOrientation() == JScrollBar.VERTICAL )
					result = zoomVerticalScrollBar( sb, zoomFactor );
			}
		}

		return( result );
	}

	protected JScrollBar zoomHorizontalScrollBar( JScrollBar hsb, double zoomFactor )
	{
		JScrollBar result = null;

		if( hsb != null )
		{
			if( _originalHorizontalPreferredScrollBarHeight < 0 )
			{
				_originalHorizontalPreferredScrollBarHeight = hsb.getPreferredSize().height;
			}

			Dimension size = hsb.getPreferredSize();
			result = super.createHorizontalScrollBar();
			result.setPreferredSize( new Dimension( size.width,
									IntegerFunctions.zoomValueCeil(_originalHorizontalPreferredScrollBarHeight, zoomFactor)
													)
									);
		}

		return( result );
	}

	public ScrollBar createHorizontalScrollBar()
	{
		ScrollBar result = new ScrollBar(JScrollBar.HORIZONTAL);
		result.switchToZoomUI();
		
		return( result );
	}

	public ScrollBar createVerticalScrollBar()
	{
		ScrollBar result = new ScrollBar(JScrollBar.VERTICAL);
		result.switchToZoomUI();

		return( result );
	}


	protected JScrollBar zoomVerticalScrollBar( JScrollBar vsb, double zoomFactor )
	{
		JScrollBar result = null;

		if( vsb != null )
		{
			if( _originalVerticalPreferredScrollBarWidth < 0 )
			{
				_originalVerticalPreferredScrollBarWidth = vsb.getPreferredSize().width;
			}

			Dimension size = vsb.getPreferredSize();
			result = super.createVerticalScrollBar();
			result.setPreferredSize( new Dimension(
										IntegerFunctions.zoomValueCeil(_originalVerticalPreferredScrollBarWidth, zoomFactor),
										size.width )
									);
		}

		return( result );
	}

	@Override
	public void switchToZoomUI()
	{
		
	}

	@Override
	public void setUI( ComponentUI ui )
	{
		super.setUI( ui );
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

	protected void initBeforeCopyingAttributes( JScrollBar sb )
	{
		if( sb instanceof ZoomComponentInterface )
		{
			ZoomComponentInterface zci = (ZoomComponentInterface) sb;
			zci.initBeforeCopyingAttributes();
		}
	}

	@Override
	public void initBeforeCopyingAttributes()
	{
		initBeforeCopyingAttributes( getHorizontalScrollBar() );
		initBeforeCopyingAttributes( getVerticalScrollBar() );
	}

	protected void initAfterCopyingAttributes( JScrollBar sb )
	{
		if( sb instanceof ZoomComponentInterface )
		{
			ZoomComponentInterface zci = (ZoomComponentInterface) sb;
			zci.initAfterCopyingAttributes();
		}
	}

	@Override
	public void initAfterCopyingAttributes()
	{
		initAfterCopyingAttributes( getHorizontalScrollBar() );
		initAfterCopyingAttributes( getVerticalScrollBar() );
	}

    protected class ScrollBar extends JScrollPane.ScrollBar
								implements ZoomComponentInterface,
											ResizeSizeComponent
    {
        /**
         * Creates a scrollbar with the specified orientation.
         * The options are:
         * <ul>
         * <li><code>ScrollPaneConstants.VERTICAL</code>
         * <li><code>ScrollPaneConstants.HORIZONTAL</code>
         * </ul>
         *
         * @param orientation  an integer specifying one of the legal
         *      orientation values shown above
         * @since 1.4
         */
        public ScrollBar(int orientation) {
            super(orientation);
        }

		@Override
		public void initBeforeCopyingAttributes()
		{
		}

		@Override
		public void initAfterCopyingAttributes()
		{
			ComponentUI ui = getUI();
			if( ui instanceof ZoomMetalScrollBarUI )
			{
				ZoomMetalScrollBarUI zmsbui = (ZoomMetalScrollBarUI) ui;
				zmsbui.initAfterCopyingAttributes();
			}
		}

		@Override
		public void switchToZoomUI()
		{
			ZoomFunctions.instance().switchToZoomUI(this, _zoomFactor);
		}

		@Override
		public void setZoomFactorReference(DoubleReference zoomFactor) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void setZoomFactor(double zoomFactor) {
		}

		@Override
		public double getZoomFactor() {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public DoubleReference getZoomFactorReference() {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void setUI( ComponentUI ui )
		{
			if( ui instanceof ScrollBarUI )
			{
				super.setUI( (ScrollBarUI) ui );
			}
			else
			{
				super.setUI( ui );
			}
		}
    }
}
