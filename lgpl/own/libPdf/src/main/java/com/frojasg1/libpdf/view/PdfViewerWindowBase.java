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
package com.frojasg1.libpdf.view;

import com.frojasg1.libpdf.view.api.PdfViewerControlView;
import com.frojasg1.libpdf.view.api.PdfViewerMaster;
import com.frojasg1.libpdf.view.panels.PdfViewerControlPanel;
import com.frojasg1.libpdf.view.panels.PdfContentPanel;
import com.frojasg1.applications.common.components.internationalization.window.InternationalizedJFrame;
import com.frojasg1.applications.common.components.resizecomp.MapResizeRelocateComponentItem;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.ExecutionFunctions;
import com.frojasg1.general.context.ApplicationContext;
import com.frojasg1.general.desktop.image.ImageFunctions;
import com.frojasg1.libpdf.api.PDFownerInterface;
import com.frojasg1.libpdf.threads.LoadPdfControllerInterface;
import com.frojasg1.libpdf.api.PdfDocumentWrapper;
import com.frojasg1.libpdf.viewer.PdfViewer;
import com.frojasg1.general.desktop.view.ContainerFunctions;
import com.frojasg1.general.desktop.view.panels.InformerInterface;
import com.frojasg1.general.desktop.view.pdf.ImageJPanel;
import com.frojasg1.general.desktop.view.pdf.ImageJPanelControllerInterface;
import com.frojasg1.general.desktop.view.zoom.mapper.ComponentMapper;
import com.frojasg1.general.desktop.view.zoom.mapper.ComposedComponent;
import com.frojasg1.general.number.DoubleReference;
import com.frojasg1.general.number.IntegerFunctions;
import com.frojasg1.libpdf.api.GlyphWrapper;
import com.frojasg1.libpdf.api.ImageWrapper;
import com.frojasg1.libpdf.view.controller.PdfObjectsSelectorObserverGen;
import com.frojasg1.libpdf.view.listeners.PdfObjectsControllerListenerBase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// Minimum Size: [570, 780]

/**
 *
 * @author Usuario
 */
public class PdfViewerWindowBase< CC extends ApplicationContext > extends InternationalizedJFrame<CC>
							implements PdfViewerMaster,
										ImageJPanelControllerInterface,
										PDFownerInterface,
										PdfViewer,
										PdfObjectsSelectorObserverGen
{
	protected static final int THICK_FOR_HOVER_GLYPH_FRAME = 1;
	protected static final int THICK_FOR_HOVER_IMAGE_FRAME = 3;

	public String _configurationBaseFileName = null;

	protected LoadPdfControllerInterface _parent = null;

	protected boolean _putSummarizedImage = false;

	protected PdfContentPanel _pdfContentPanel = null;

	protected PdfViewerControlView _pdfViewerControlView = null;

	protected PdfObjectsControllerListenerBase _pdfObjectsControllerListener = null;

	protected Rectangle _hoverImageBoundsOnImageJPanel = null;
	protected Rectangle _hoverGlyphBoundsOnImageJPanel = null;

	protected ComponentListener _componentListener = null;

	protected ImageWrapper _hoverImage = null;
	protected GlyphWrapper _hoverGlyph = null;

	/**
	 * Creates new form MainWindow
	 */
	public PdfViewerWindowBase( BaseApplicationConfigurationInterface appliConf )
	{
		super( appliConf );
	}

	public PdfViewerWindowBase( BaseApplicationConfigurationInterface appliConf,
								CC applicationContext )
	{
		super( appliConf, applicationContext );
	}

	public void init( LoadPdfControllerInterface parent,
						String configurationBaseFileName )
	{
		_parent = parent;

		_configurationBaseFileName = configurationBaseFileName;

		initComponents();

		initPanels();

		SwingUtilities.invokeLater( () -> setWindowConfiguration( ) );
	}

	protected Rectangle getHoverImageBounds()
	{
		return( _hoverImageBoundsOnImageJPanel );
	}

	protected Rectangle getHoverGlyphBounds()
	{
		return( _hoverGlyphBoundsOnImageJPanel );
	}

	protected ImageWrapper getHoverImage()
	{
		return( _hoverImage );
	}

	protected GlyphWrapper getHoverGlyph()
	{
		return( _hoverGlyph );
	}

	protected PdfObjectsControllerListenerBase getPdfObjectsControllerListener()
	{
		return( _pdfObjectsControllerListener );
	}

	protected Rectangle getBoundsOnImageJPanel( Rectangle normalizedRect )
	{
		Rectangle result = getPdfObjectsControllerListener().getBoundsOnImageJPanel(normalizedRect);

		return( result );
	}

	@Override
	public String getPdfFileName()
	{
		return( _pdfContentPanel.getPdfFileName() );
	}

	@Override
	public int getNumPages()
	{
		return( _pdfContentPanel.getNumPages() );
	}

	public void setNewPDF( PdfDocumentWrapper document )
	{
//		_pageSegmentator = null;

		_pdfContentPanel.setNewPDF( document );
		setTitle( getPdfFileName() );
		setDocument( document );

//		setNumberOfPages( getNumPages() );
//		setRenderSegmentsOfPages( hasToShowSegments() );
	}

	protected void setDocument( PdfDocumentWrapper document )
	{
		_pdfContentPanel.setDocument( document );
		updatePage(0);
	}

	public PdfDocumentWrapper getPdfDocument()
	{
		return( _pdfContentPanel.getPdfDocument() );
	}
/*
	protected void setImage( String fileName )
	{
		try
		{
			File file = new File( fileName );
			newPageSet( ImageIO.read( file ) );
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}
	}

	public void setNumberOfPages( int numberOfPages )
	{
		_totalNumberOfPages = numberOfPages;
		_currentPage = 0;

		updatePage( 0 );
	}
*/
	public int getCurrentPageIndex()
	{
		return( _pdfContentPanel.getCurrentPage() );
	}

	public void doNewPageTasks()
	{
		updateCurrentPageTexts();

		updatePdfObjects( getCurrentPageIndex() );
		_pdfObjectsControllerListener.resetSelection();
	}

	protected void updatePdfObjects( int pageIndex )
	{
		List<GlyphWrapper> glyphs = ExecutionFunctions.instance().safeFunctionExecution( () -> getPdfDocument().getGlyphsOfPage(pageIndex) );
		List<ImageWrapper> images = ExecutionFunctions.instance().safeFunctionExecution( () -> getPdfDocument().getImagesOfPage(pageIndex) );
		_pdfObjectsControllerListener.setGlyphsOfPage(glyphs);
		_pdfObjectsControllerListener.setImagesOfPage(images);
	}

	public void updateCurrentPageTexts()
	{
		_pdfViewerControlView.updateCurrentPageTexts( getCurrentPageIndex() + 1, getNumPages() );
	}

	public void updatePage( int pageIndex )
	{
//		if( isVisible() )
		_pdfContentPanel.updatePage( pageIndex, getSelectedZoomFactor() );

		doNewPageTasks();
	}

	protected PdfViewerControlView createPdfViewerControlView()
	{
		PdfViewerControlPanel result = new PdfViewerControlPanel();
		result.init( this );

		return( result );
	}
/*
	protected PdfContentPanel createPdfContentPanel()
	{
		PdfContentPanel result = new PdfContentPanel();
		result.init( this, this );

		return( result );
	}
*/

	protected PdfContentPanel createPdfContentPanel()
	{
		PdfContentPanel result = new PdfContentPanel(){
			@Override
			protected ImageJPanel createImageJPanel()
			{
				return( PdfViewerWindowBase.this.createImageJPanel(getImageScrollPane()) );
			}
		};
		result.init( this, this );

		return( result );
	}

	protected ImageJPanel createImageJPanel( JScrollPane scrollPane )
	{
		ImageJPanel result = new ImageJPanel( scrollPane, this ) {

			@Override
			public void paint( Graphics grp )
			{
				synchronized( this )
				{
					BufferedImage image = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB );
					Graphics grp2 = image.createGraphics();

					super.paint( grp2 );

					paintFramesBase( grp2 );

					paintAdditionalElementsOfPage( grp2 );

					grp.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null );
					grp2.dispose();
				}
			}
		};

		return( result );
	}

	protected void paintHoverImageFrame( Graphics grp )
	{
		paintFrame(grp, getHoverImageBounds(), getThickForHoverImageFrame(), Color.BLUE );
	}

	protected void paintHoverGlyphFrame( Graphics grp )
	{
		paintFrame(grp, getHoverGlyphBounds(), getThickForHoverGlyphFrame(), Color.RED );
	}

	protected void paintFramesBase( Graphics grp )
	{
		paintHoverImageFrame( grp );
		paintHoverGlyphFrame( grp );
	}

	// to be implemented by derived class
	protected void paintAdditionalElementsOfPage( Graphics grp )
	{
	}

	protected Component getControlViewComponent()
	{
		return( (Component) _pdfViewerControlView );
	}

	protected ComposedComponent getControlViewComposedComponent()
	{
		return( (ComposedComponent) _pdfViewerControlView );
	}

	protected PdfViewerControlView getControlView()
	{
		return( _pdfViewerControlView );
	}

	protected PdfContentPanel getPdfContentPanel()
	{
		return( _pdfContentPanel );
	}

	protected ImageJPanel getPdfContentImageJPanel()
	{
		ImageJPanel result = null;
		if( getPdfContentPanel() != null )
			result = getPdfContentPanel().getImagePanel();

		return( result );
	}

	protected void initPanels()
	{
		try
		{
			int yy = 0;
			Container contentPane = getContentPane();
			_pdfViewerControlView = createPdfViewerControlView();
			contentPane.add( getControlViewComponent() );
			Dimension size = getControlViewComposedComponent().getInternalSize();
			getControlViewComponent().setBounds( 0, yy, size.width, size.height );
			yy += size.height;

			jPanel2.setBounds( 0, yy, size.width, jPanel2.getHeight() );
			_pdfContentPanel = createPdfContentPanel();
			ContainerFunctions.instance().addComponentToCompletelyFillParent(jPanel2, _pdfContentPanel);

			pack();
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}
	}

	protected void setWindowConfiguration( )
	{
		MapResizeRelocateComponentItem mapRRCI = new MapResizeRelocateComponentItem();
		try
		{
			mapRRCI.putResizeRelocateComponentItem( _pdfContentPanel, ResizeRelocateItem.FILL_WHOLE_PARENT );
			mapRRCI.putAll( getControlViewComposedComponent().getResizeRelocateInfo() );

			mapRRCI.putResizeRelocateComponentItem( jPanel2, ResizeRelocateItem.RESIZE_TO_RIGHT + ResizeRelocateItem.RESIZE_TO_BOTTOM );
			mapRRCI.putResizeRelocateComponentItem( _pdfContentPanel, ResizeRelocateItem.FILL_WHOLE_PARENT );
			mapRRCI.putAll( _pdfContentPanel.getResizeRelocateInfo() );
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}

		createInternationalization(	getAppliConf().getConfigurationMainFolder(),
									getAppliConf().getApplicationNameFolder(),
									getAppliConf().getApplicationGroup(),
									getAppliConf().getInternationalPropertiesPathInJar(),
									_configurationBaseFileName,
									this,
									null,
									null,
									true,
									mapRRCI );
	}


	@Override
	public void internationalizationInitializationEndCallback()
	{
		super.internationalizationInitializationEndCallback();

		addListeners();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(570, 340));
        setName(""); // NOI18N
        getContentPane().setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(510, 250));
        jPanel2.setLayout(null);
        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 550, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
/*
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(PdfViewerWindowBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PdfViewerWindowBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PdfViewerWindowBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PdfViewerWindowBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>
        //</editor-fold>
*/
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
//				PdfViewerWindowBase window = new PdfViewerWindowBase();
//				window.setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

	protected DoubleReference getSelectedZoomFactor()
	{
		return( _pdfViewerControlView.getSelectedZoomFactor() );
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
	public void newPageSet( BufferedImage image, DoubleReference factor, int pageIndex )
	{
//		_pdfContentPanel.setPage( image, factor, pageIndex );
		updateZoomComboBoxValues();

		_pdfViewerControlView.setZoomSelectedItem( _pdfContentPanel.getZoomFactor() );
	}
/*
	protected void newPageSet( BufferedImage image )
	{
		DoubleReference factor = new DoubleReference( 1.0D );
		newPageSet( image, factor, getCurrentPageIndex() );
	}
*/
	protected void updateZoomComboBoxValues()
	{
		List<DoubleReference> listOfZoomFactors = _pdfContentPanel.getStepsForFactorList();
		_pdfViewerControlView.updateZoomComboBoxValues(listOfZoomFactors);
	}

	protected void updateZoomFactor()
	{
		setNewPdfZoomFactor( getSelectedZoomFactor() );
	}

	protected void navigatorTask( Runnable navigatorUpdateFunction )
	{
		updateZoomFactor();

		navigatorUpdateFunction.run();

		doNewPageTasks();
	}

	@Override
	public void navigator_start( InformerInterface panel )
	{
		navigatorTask( () -> _pdfContentPanel.navigator_start(panel) );
	}

	@Override
	public void navigator_end( InformerInterface panel )
	{
		navigatorTask( () -> _pdfContentPanel.navigator_end(panel) );
	}

	@Override
	public void navigator_previous( InformerInterface panel )
	{
		navigatorTask( () -> _pdfContentPanel.navigator_previous(panel) );
	}

	@Override
	public void navigator_next( InformerInterface panel )
	{
		navigatorTask( () -> _pdfContentPanel.navigator_next(panel) );
	}

	@Override
	public void previousPage()
	{
		navigatorTask( () -> _pdfContentPanel.previousPage() );
	}

	@Override
	public void nextPage()
	{
		navigatorTask( () -> _pdfContentPanel.nextPage() );
	}

	@Override
	public void setNewPdfZoomFactor( DoubleReference factor )
	{
		_pdfContentPanel.setNewPdfZoomFactor(factor);
	}
/*
	protected void factorUpdatePdfImagePanel( DoubleReference factor )
	{
        if( factor != null )
        {
			_pdfImagePanel.setImage( getPage( _currentPage, factor._value ), factor );
        }
	}
*/	
	
	@Override
	public BufferedImage getPage( int pageIndex, double factor )
	{
		BufferedImage image = null;

		image = _pdfContentPanel.getPage( pageIndex, factor );

		return( image );
	}

	public void block()
	{
		changeToWaitCursor();
	}

	public void unblock()
	{
		revertChangeToWaitCursor();
	}

	@Override
	public BaseApplicationConfigurationInterface getAppliConf()
	{
		return( _appliConf );
	}

	public void closeWindow()
	{
		boolean closeWindow = false;
		formWindowClosing( closeWindow );
		setVisible(false);
	}

	@Override
	public void changeZoomFactor( double zoomFactor )
	{
		double previousZoomFactor = _previousZoomFactor;
		
		super.changeZoomFactor( zoomFactor );

		DoubleReference newFactor = new DoubleReference( getSelectedZoomFactor()._value * zoomFactor / previousZoomFactor );
		setNewPdfZoomFactor( newFactor );
	}

	@Override
	public void formWindowClosingEvent( )
	{
		closeWindow();
	}

	@Override
	protected void translateMappedComponents(ComponentMapper compMapper)
	{
		jPanel2 = compMapper.mapComponent( jPanel2 );
	}

	@Override
	public void showPage(int index)
	{
		if( ( index >= 0 ) && ( index < getNumPages() ) )
			updatePage( index );
	}

	@Override
	public void newPageSelected(int newPageIndex)
	{
		if( newPageIndex > -1 )
		{
//			doNewPageTasks();
//			_pdfContentPanel.updatePage( newPageIndex, getSelectedZoomFactor() );
			updatePage( newPageIndex );
		}
	}

	@Override
	public void newPdfZoomFactorSelected(DoubleReference newFactor)
	{
		setNewPdfZoomFactor( newFactor );
	}

	@Override
	public void releaseResources()
	{
		removeListeners();
		_pdfObjectsControllerListener = null;

		super.releaseResources();
	}

	public void resetHover()
	{
		_hoverImageBoundsOnImageJPanel = null;
		_hoverGlyphBoundsOnImageJPanel = null;

		getPdfObjectsControllerListener().resetSelection();
	}

	protected ComponentListener createComponentListener()
	{
		ComponentListener result = new ComponentAdapter() {
			@Override
			public void componentMoved( ComponentEvent evt )
			{
				resetHover();
			}
		};

		return( result );
	}

	protected void addListeners()
	{
		_pdfObjectsControllerListener = createPdfObjectsControllerListener();
		_componentListener = createComponentListener();
		addComponentListener( _componentListener );
	}

	protected void removeListeners()
	{
		_pdfObjectsControllerListener.releaseResources();
		removeComponentListener( _componentListener );
	}

	public PdfObjectsControllerListenerBase createPdfObjectsControllerListener()
	{
		PdfObjectsControllerListenerBase result = new PdfObjectsControllerListenerBase();
		result.init( getPdfContentPanel().getImagePanel(), this);

		return( result );
	}

	protected int getThickForHoverGlyphFrame()
	{
		return( IntegerFunctions.zoomValueRound(THICK_FOR_HOVER_GLYPH_FRAME, getZoomFactor() ) );
	}

	protected int getThickForHoverImageFrame()
	{
		return( IntegerFunctions.zoomValueRound(THICK_FOR_HOVER_IMAGE_FRAME, getZoomFactor() ) );
	}

	protected void paintFrame( Graphics grp2, Rectangle hoverScreenBounds, int thick, Color color )
	{
		if( hoverScreenBounds != null )
		{
//			Point locationOnScreen = getLocationOnScreen();
//			int xx = hoverScreenBounds.x - locationOnScreen.x;
//			int yy = hoverScreenBounds.y - locationOnScreen.y;
			int xx = hoverScreenBounds.x;
			int yy = hoverScreenBounds.y;

			ImageFunctions.instance().drawRect(grp2, xx, yy,
				hoverScreenBounds.width, hoverScreenBounds.height, color, thick);
		}
	}

	@Override
	public void newImage(ImageWrapper image, Rectangle bounds)
	{
		_hoverImage = image;
		_hoverImageBoundsOnImageJPanel = bounds;
		SwingUtilities.invokeLater( () -> getPdfContentImageJPanel().repaint() );
	}

	@Override
	public void newGlyph(GlyphWrapper glyph, Rectangle bounds)
	{
		_hoverGlyph = glyph;
		_hoverGlyphBoundsOnImageJPanel = bounds;
		getPdfContentImageJPanel().repaint();
	}
}