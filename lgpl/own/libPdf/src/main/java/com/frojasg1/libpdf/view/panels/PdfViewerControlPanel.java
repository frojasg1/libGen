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
package com.frojasg1.libpdf.view.panels;

import com.frojasg1.applications.common.components.resizecomp.MapResizeRelocateComponentItem;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem;
import com.frojasg1.general.desktop.view.panels.InformerInterface;
import com.frojasg1.general.desktop.view.panels.NavigatorControllerInterface;
import com.frojasg1.general.desktop.view.panels.NavigatorJPanel;
import com.frojasg1.general.desktop.view.zoom.mapper.ComponentMapper;
import com.frojasg1.general.desktop.view.zoom.mapper.ComposedComponent;
import com.frojasg1.general.number.DoubleReference;
import com.frojasg1.libpdf.view.api.PdfViewerControlView;
import com.frojasg1.libpdf.view.api.PdfViewerMaster;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class PdfViewerControlPanel extends com.frojasg1.general.desktop.view.panels.CustomJPanel
	implements ComposedComponent, PdfViewerControlView,
				NavigatorControllerInterface, InformerInterface
{
	protected PdfViewerMaster _controller = null;

	protected NavigatorJPanel _navigatorPanel = null;

	protected boolean _flagComboBoxItemSelectedByProgram = false;

	protected MapResizeRelocateComponentItem _resizeRelocateInfo = null;

	/**
	 * Creates new form PdfViewerControlPanel
	 */
	public PdfViewerControlPanel() {
	}

	public void init( PdfViewerMaster controller )
	{
		super.init();

		_controller = controller;

		initComponents();

		initPanels();

		setWindowConfiguration();
	}

	protected void initPanels()
	{
		try
		{
//			SwingUtilities.invokeLater( () -> {

				_navigatorPanel = new NavigatorJPanel( this );
				_navigatorPanel.setBounds(0, 0, 220, 50);
				_navigatorPanel.setBorder( jPanel3.getBorder() );
				jPanel3.add( _navigatorPanel );
//			});
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}
	}

	protected void setWindowConfiguration( )
	{
		_resizeRelocateInfo = new MapResizeRelocateComponentItem();
		MapResizeRelocateComponentItem mapRRCI = _resizeRelocateInfo;
		try
		{
			mapRRCI.putResizeRelocateComponentItem( jPanel1, ResizeRelocateItem.FILL_WHOLE_PARENT );
			mapRRCI.putResizeRelocateComponentItem( jPanel3, 0 );

			mapRRCI.putResizeRelocateComponentItem( _navigatorPanel, ResizeRelocateItem.RESIZE_TO_RIGHT +
																	ResizeRelocateItem.RESIZE_TO_BOTTOM );
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}

//		ExecutionFunctions.instance().safeMethodExecution( () -> registerInternationalizedStrings() );
	}

	@Override
	public MapResizeRelocateComponentItem getResizeRelocateInfo()
	{
		return( _resizeRelocateInfo );
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLZoomFactor = new javax.swing.JLabel();
        jCBZoomFactor = new javax.swing.JComboBox();
        jLPage = new javax.swing.JLabel();
        jTF_currentPage = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTFTotalPages = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();

        setLayout(null);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(510, 50));
        jPanel1.setLayout(null);

        jLZoomFactor.setText("Zoom factor:");
        jLZoomFactor.setName("jLZoomFactor"); // NOI18N
        jPanel1.add(jLZoomFactor);
        jLZoomFactor.setBounds(10, 10, 110, 14);

        jCBZoomFactor.setName("jCBZoomFactor"); // NOI18N
        jCBZoomFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBZoomFactorActionPerformed(evt);
            }
        });
        jPanel1.add(jCBZoomFactor);
        jCBZoomFactor.setBounds(10, 30, 90, 20);

        jLPage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLPage.setText("Page:");
        jLPage.setName("jLPage"); // NOI18N
        jPanel1.add(jLPage);
        jLPage.setBounds(355, 25, 65, 14);

        jTF_currentPage.setText("0");
        jTF_currentPage.setName(""); // NOI18N
        jTF_currentPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_currentPageActionPerformed(evt);
            }
        });
        jPanel1.add(jTF_currentPage);
        jTF_currentPage.setBounds(425, 25, 30, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("/");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(455, 25, 10, 20);

        jTFTotalPages.setEditable(false);
        jTFTotalPages.setEnabled(false);
        jTFTotalPages.setFocusable(false);
        jTFTotalPages.setName(""); // NOI18N
        jPanel1.add(jTFTotalPages);
        jTFTotalPages.setBounds(465, 25, 30, 20);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setName("jPanel3_test"); // NOI18N
        jPanel3.setLayout(null);
        jPanel1.add(jPanel3);
        jPanel3.setBounds(130, 10, 220, 50);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 550, 70);
    }// </editor-fold>//GEN-END:initComponents

    private void jCBZoomFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBZoomFactorActionPerformed
        // TODO add your handling code here:

        if( ! _flagComboBoxItemSelectedByProgram )
        {
            DoubleReference newFactor = getSelectedZoomFactor();
            _controller.newPdfZoomFactorSelected( newFactor );
        }
    }//GEN-LAST:event_jCBZoomFactorActionPerformed

    private void jTF_currentPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_currentPageActionPerformed
        // TODO add your handling code here:

        int numberOfPage = -1;
        try
        {
            numberOfPage = Integer.parseInt( jTF_currentPage.getText() );
        }
        catch( Throwable th )
        {}

        if( numberOfPage > -1 )
        {
            _controller.newPageSelected( numberOfPage - 1 );
        }

    }//GEN-LAST:event_jTF_currentPageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jCBZoomFactor;
    private javax.swing.JLabel jLPage;
    private javax.swing.JLabel jLZoomFactor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTFTotalPages;
    private javax.swing.JTextField jTF_currentPage;
    // End of variables declaration//GEN-END:variables

	@Override
	public DoubleReference getSelectedZoomFactor()
	{
        DoubleReference newFactor = null;
        Object obj = jCBZoomFactor.getSelectedItem();
        if( obj instanceof DoubleReference )
        {
            newFactor = (DoubleReference) obj;
        }
		return( newFactor );
	}

	protected double getSelectedZoomFactorDouble()
	{
		double factor = 1.0D;
		
		DoubleReference dr = getSelectedZoomFactor();
		if( dr != null )
			factor = dr._value;
		
		return( factor );
	}

	@Override
	public void updateZoomComboBoxValues( List<DoubleReference> listOfZoomFactors )
	{
		if( listOfZoomFactors != null )
		{
			DefaultComboBoxModel<DoubleReference> dcbm = new DefaultComboBoxModel<DoubleReference>( new Vector<DoubleReference>( listOfZoomFactors ) );
			jCBZoomFactor.setModel( dcbm );
		}
	}

	@Override
	public void updateCurrentPageTexts( int currentPage, int numPages )
	{
		jTF_currentPage.setText( String.valueOf( currentPage ) );
		jTFTotalPages.setText( String.valueOf( numPages ) );
	}

	@Override
	public void setZoomSelectedItem(DoubleReference selectedItem) {
		_flagComboBoxItemSelectedByProgram = true;
		jCBZoomFactor.setSelectedItem( selectedItem );
		_flagComboBoxItemSelectedByProgram = false;
	}

	@Override
	public Dimension getInternalSize()
	{
		return( jPanel1.getSize() );
	}

	@Override
	public void setComponentMapper(ComponentMapper compMapper)
	{
		jCBZoomFactor = compMapper.mapComponent( jCBZoomFactor );
		jLPage = compMapper.mapComponent( jLPage );
		jLZoomFactor = compMapper.mapComponent( jLZoomFactor );
		jLabel1 = compMapper.mapComponent( jLabel1 );
		jPanel1 = compMapper.mapComponent( jPanel1 );
		jPanel3 = compMapper.mapComponent( jPanel3 );
		jTFTotalPages = compMapper.mapComponent( jTFTotalPages );
		jTF_currentPage = compMapper.mapComponent( jTF_currentPage );
	}

	@Override
	public void navigator_start(InformerInterface panel)
	{
		_controller.navigator_start(this);
	}

	@Override
	public void navigator_end(InformerInterface panel)
	{
		_controller.navigator_end(this);
	}

	@Override
	public void navigator_previous(InformerInterface panel)
	{
		_controller.navigator_previous(this);
	}

	@Override
	public void navigator_next(InformerInterface panel)
	{
		_controller.navigator_next(this);
	}

	@Override
	public Rectangle getInternalBounds()
	{
		return( jPanel1.getBounds() );
	}
}
