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
package com.frojasg1.general.desktop.generic.dialogs.impl;

import com.frojasg1.applications.common.components.zoom.SwitchToZoomComponents;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.applications.common.configuration.application.ConfigurationForFileChooserInterface;
import com.frojasg1.general.desktop.classes.Classes;
import com.frojasg1.general.desktop.view.ViewFunctions;
import com.frojasg1.general.desktop.view.zoom.imp.ZoomIconImp;
import com.frojasg1.general.desktop.view.zoom.ui.ZoomMetalFileChooserUI;
import com.frojasg1.general.number.DoubleReference;
import com.frojasg1.general.number.IntegerFunctions;
import com.frojasg1.general.reflection.ReflectionFunctions;
import com.frojasg1.general.zoom.ZoomInterface;
import com.frojasg1.generic.GenericFunctions;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicComboPopup;
import sun.awt.shell.ShellFolder;

/**
 *
 * @author Usuario
 */
public class StaticDesktopDialogsWrapper
{
	public static final int OPEN = 0;
	public static final int SAVE = 1;

	protected static StaticDesktopDialogsWrapper _instance = null;

	public static StaticDesktopDialogsWrapper instance()
	{
		if( _instance == null )
			_instance = new StaticDesktopDialogsWrapper();
		
		return( _instance );
	}

    protected StaticDesktopDialogsWrapper(){}

	static public void M_changeFontToApplicationFontSize_forComponent_static( double zoomFactor, Component comp )
	{
		instance().M_changeFontToApplicationFontSize_forComponent( zoomFactor, comp );
	}

	static public void showMessageDialog_static( Component parent, Object message, String title,
											int messageType, ZoomInterface conf )
	{
		instance().showMessageDialog( parent, message, title, messageType, conf );
	}

	/**
	 * 
	 * @param parent
	 * @param message
	 * @param title
	 * @param messageType    an example for this parameter: JOptionPane.ERROR_MESSAGE
	 * @param conf 
	 */
	static public void showMessageDialog_static( Component parent, Object message, String title,
											int messageType, double zoomFactor )
	{
		instance().showMessageDialog( parent, message, title, messageType, zoomFactor );
	}

	static public int showOptionDialog_static( Component parentComponent, Object message, String title, int optionType,
										int messageType, Icon icon, Object[] options, Object initialValue,
										ZoomInterface conf )
	{
		return( instance().showOptionDialog( parentComponent, message, title, optionType,
										messageType, icon, options, initialValue,
										conf ) );
	}

	/**
	 * 
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param optionType		an example for this parameter: JOptionPane.YES_NO_CANCEL_OPTION
	 * @param messageType		an example for this parameter: JOptionPane.QUESTION_MESSAGE
	 * @param icon
	 * @param options
	 * @param initialValue
	 * @param conf
	 * @return 
	 */
	static public int showOptionDialog_static( Component parentComponent, Object message, String title, int optionType,
										int messageType, Icon icon, Object[] options, Object initialValue,
										double zoomFactor )
	{
		return( instance().showOptionDialog( parentComponent, message, title, optionType,
										messageType, icon, options, initialValue,
										zoomFactor ) );
	}

	/**
	 * 
	 * @param parent
	 * @param open_save_dialog	. This parameter has to contain either StaticDesktopDialogsWrapper.OPEN or
								StaticDesktopDialogsWrapper.SAVE
	 * @param conf
	 * @param fileFilterList
	 * @return 
	 */
	static public String showFileChooserDialog_static( Component parent,
													int open_save_dialog,
													ConfigurationForFileChooserInterface conf,
													List<FileFilter> fileFilterList,
													String defaultFileName )
	{
		return( instance().showFileChooserDialog( parent,
													open_save_dialog,
													conf,
													fileFilterList,
													defaultFileName ) );
	}

	/**
	 * 
	 * @param parent
	 * @param open_save_dialog	. This parameter has to contain either StaticDesktopDialogsWrapper.OPEN or
								StaticDesktopDialogsWrapper.SAVE
	 * @param conf
	 * @param fileFilterList
	 * @return 
	 */
	static public String showFileChooserDialog_static( Component parent,
													DesktopFileChooserParameters dfcp,
													ConfigurationForFileChooserInterface conf )
	{
		return( instance().showFileChooserDialog( parent,
													dfcp,
													conf ) );
	}

	public static Point getCenteredLocationForComponent_static( Component comp )
	{
		return( instance().getCenteredLocationForComponent( comp ) );
	}

	
	protected void M_changeFontToApplicationFontSize( Component comp, double factor )
	{
		M_changeFontToApplicationFontSize( comp, factor, 0, -1 );
	}

	protected void zoomScrollBar( JScrollBar sb, double zoomFactor )
	{
		if( sb != null )
		{
			if( sb.getOrientation() == JScrollBar.HORIZONTAL )
				zoomHorizontalScrollBar( sb, zoomFactor );
			else if( sb.getOrientation() == JScrollBar.VERTICAL )
				zoomVerticalScrollBar( sb, zoomFactor );
		}
	}

	protected void zoomHorizontalScrollBar( JScrollBar hsb, double zoomFactor )
	{
		if( hsb != null )
		{
			Dimension size = hsb.getPreferredSize();
			hsb.setPreferredSize( new Dimension( size.width,
									IntegerFunctions.zoomValueCeil(hsb.getPreferredSize().height, zoomFactor)
													)
									);
		}
	}
	
	protected void zoomVerticalScrollBar( JScrollBar vsb, double zoomFactor )
	{
		if( vsb != null )
		{
			Dimension size = vsb.getPreferredSize();
			vsb.setPreferredSize( new Dimension(
										IntegerFunctions.zoomValueCeil( vsb.getPreferredSize().width, zoomFactor),
										size.width )
									);
		}
	}

	protected void M_changeFontToApplicationFontSize( Component comp,
														double zoomFactor,
														int level,
														int fromLevelToZoomFont )
	{
//		System.out.println( StringFunctions.instance().buildStringFromRepeatedChar( ' ', level * 4) +
//							comp.getClass().getName() );

		if( fromLevelToZoomFont < level )
		{
			Font newFont = M_getNewFontForComponentFromApplicationFontSize(comp, zoomFactor );
			if( newFont != null )
			{
				comp.setFont( newFont );
			}
		}

		Class<?> filePaneClass = Classes.getFilePaneClass();	// JRE 7, 8, ...

		if( comp instanceof JComboBox )
		{
			JComboBox combo = (JComboBox) comp;
			BasicComboPopup popup = (BasicComboPopup) combo.getUI().getAccessibleChild(combo, 0);

			M_changeFontToApplicationFontSize( popup, zoomFactor, level + 1, level + 4 );
		}
//		else if( comp instanceof FilePane )
		else if( ( filePaneClass != null ) && filePaneClass.isInstance( comp ) )	// JRE 7, 8, ...
		{
//			JPopupMenu popupMenu = fp.getComponentPopupMenu();
			JPopupMenu popupMenu = (JPopupMenu) ReflectionFunctions.instance().invokeMethod( "getComponentPopupMenu",
																							comp.getClass(), comp );

			M_changeFontToApplicationFontSize( popupMenu, zoomFactor, level + 1, -1 );
		}
		else if( comp instanceof JScrollBar )
		{
			zoomScrollBar( (JScrollBar) comp, zoomFactor );
		}
		else if( comp instanceof JLabel )
		{
			JLabel jlabel = (JLabel) comp;
			Icon icon = jlabel.getIcon();
			if( icon != null )
			{
				boolean centerSmaller = true;
				ZoomIconImp zi = new ZoomIconImp( icon, new DoubleReference( zoomFactor ), centerSmaller );
				jlabel.setIcon( zi );

				Dimension newDimension = ViewFunctions.instance().getNewDimension(jlabel.getSize(),
																				null, zoomFactor );
				jlabel.setSize(newDimension);
			}
		}

		if( comp instanceof Container	)
		{
			Container contnr = (Container) comp;
			for( int ii=0; ii<contnr.getComponentCount(); ii++ )
			{
				M_changeFontToApplicationFontSize(contnr.getComponent(ii), zoomFactor, level + 1, fromLevelToZoomFont );
			}

			if( contnr instanceof JMenu )
			{
				JMenu jmnu = (JMenu) contnr;
				for( int ii=0; ii<jmnu.getMenuComponentCount(); ii++ )
					M_changeFontToApplicationFontSize(jmnu.getMenuComponent( ii ), zoomFactor, level + 1, fromLevelToZoomFont );
			}
		}
	}

	public void M_changeFontToApplicationFontSize_forComponent( double zoomFactor, Component comp )
	{
//		double factor = 0.0D;
		double factor = zoomFactor;

		if( factor > 0.0F )
		{
//			System.out.println( String.format( "%n%n%nAntes%n=====" ) +
//								ViewFunctions.instance().traceComponentTree(comp, (component) -> "preferredSize : " + component.getPreferredSize() + "     size : " + component.getSize() ));

			M_changeFontToApplicationFontSize( comp, factor );

//			System.out.println( ViewFunctions.instance().traceComponentTree(comp));

			SwitchToZoomComponents stzc = new SwitchToZoomComponents( null, null );
			stzc.setZoomFactor(comp, zoomFactor, (comp_) -> {
				boolean result = ( comp_ != null ) && ( comp_.getClass().getName().endsWith( "AlignedLabel" ) );
				return( result );
			});

//			System.out.println( String.format( "%n%n%n" ) +
//								ViewFunctions.instance().traceComponentTree(comp, (component) -> stzc.getComponentResizingResult().get(component) ));

//			if( ( comp instanceof JFileChooser ) ||
//				( comp instanceof JOptionPane ) )
			if( comp instanceof JFileChooser )
			{
				Dimension dim = comp.getPreferredSize();
				int width = (int) ( ( (float) dim.getWidth()) * factor );
				int height = (int) ( ( (float) dim.getHeight()) * factor );
				Dimension newDim = new Dimension( width, height);

//				comp.setPreferredSize( newDim );
			}

//			System.out.println( ViewFunctions.instance().traceComponentTree(comp, (comp_) -> getInfoString(comp_) ) );

//			System.out.println( String.format( "%n%n%nDespues%n=======" ) +
//								ViewFunctions.instance().traceComponentTree(comp, (component) -> "preferredSize : " + component.getPreferredSize() + "     size : " + component.getSize() ));
		}
	}

	protected String getInfoString( Component comp )
	{
		String result = "";
		if( comp instanceof Container )
		{
			Container cont = (Container) comp;
			Insets insets = cont.getInsets();
			if( insets != null )
			{
				result = " insets - " + insets;
			}

			LayoutManager lm = cont.getLayout();
			if( lm != null )
			{
				result += "        " + lm.getClass().getName();
				if( lm instanceof BorderLayout )
				{
					result += " ( " + lm + " )";
				}
			}
		}
		return( result );
	}

	public void showMessageDialog( Component parent, Object message, String title,
											int messageType, ZoomInterface conf )
	{
		showMessageDialog( parent, message, title, messageType, conf.getZoomFactor() );
	}

	/**
	 * 
	 * @param parent
	 * @param message
	 * @param title
	 * @param messageType    an example for this parameter: JOptionPane.ERROR_MESSAGE
	 * @param conf 
	 */
	public void showMessageDialog( Component parent, Object message, String title,
											int messageType, double zoomFactor )
	{
		JOptionPane option = new JOptionPane(	message, messageType );
		M_changeFontToApplicationFontSize_forComponent( zoomFactor, option );
		JDialog dialog = option.createDialog(parent, title );
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	public int showOptionDialog( Component parentComponent, Object message, String title, int optionType,
										int messageType, Icon icon, Object[] options, Object initialValue,
										ZoomInterface conf )
	{
		return( showOptionDialog( parentComponent, message, title, optionType, messageType, icon, options, initialValue,
									conf.getZoomFactor() ) );
	}

	/**
	 * 
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param optionType		an example for this parameter: JOptionPane.YES_NO_CANCEL_OPTION
	 * @param messageType		an example for this parameter: JOptionPane.QUESTION_MESSAGE
	 * @param icon
	 * @param options
	 * @param initialValue
	 * @param conf
	 * @return 
	 */
	public int showOptionDialog( Component parentComponent, Object message, String title, int optionType,
										int messageType, Icon icon, Object[] options, Object initialValue,
										double zoomFactor )
	{
		JOptionPane option = new JOptionPane(	message, messageType );
		option.setIcon(icon);
		option.setOptionType(optionType);
		option.setOptions( options );
		option.setInitialValue( initialValue );
		M_changeFontToApplicationFontSize_forComponent( zoomFactor, option );
		JDialog dialog = option.createDialog(parentComponent, title );
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);

		int result = -1;
		if( options != null )
		{
			for( int ii=0; (result==-1) && ii<options.length; ii++ )
				if( option.getValue() == options[ii] ) result = ii;
		}
		return( result );
	}
	
	/**
	 * 
	 * @param parent
	 * @param open_save_dialog	. This parameter has to contain either StaticDesktopDialogsWrapper.OPEN or
								StaticDesktopDialogsWrapper.SAVE
	 * @param conf
	 * @param fileFilterList
	 * @return 
	 */
	public String showFileChooserDialog( Component parent,
													int open_save_dialog,
													ConfigurationForFileChooserInterface conf,
													List<FileFilter> fileFilterList,
													String defaultFileName )
	{
		String result = null;

		DesktopFileChooserParameters dfcp = new DesktopFileChooserParameters();
		dfcp.setDefaultFileName( defaultFileName );
		dfcp.setListOfFileFilter(fileFilterList);
		dfcp.setOpenOrSaveDialog( open_save_dialog );
		setDefaultLocale( dfcp );

		result = showFileChooserDialog( parent, dfcp, conf );

		return( result );
	}

	protected void setDefaultLocale( DesktopFileChooserParameters dfcp ) {
		Locale locale = dfcp.getLocale();
		if( locale == null )
		{
			BaseApplicationConfigurationInterface appliConf = GenericFunctions.instance().getAppliConf();
			String language = appliConf.getLanguage();
			locale = GenericFunctions.instance().getObtainAvailableLanguages().getLocaleOfLanguage( language );
			dfcp.setLocale( locale );
		}
	}

	/**
	 * 
	 * @param parent
	 * @param open_save_dialog	. This parameter has to contain either StaticDesktopDialogsWrapper.OPEN or
								StaticDesktopDialogsWrapper.SAVE
	 * @param conf
	 * @param fileFilterList
	 * @return 
	 */
	public String showFileChooserDialog( Component parent,
													DesktopFileChooserParameters dfcp,
													ConfigurationForFileChooserInterface conf )
	{
		String result = null;

		int open_save_dialog = dfcp.getOpenOrSaveDialog();
		List<FileFilter> fileFilterList = dfcp.getListOfFileFilter();
		String defaultFileName = dfcp.getDefaultFileName();
		int mode = dfcp.getMode();
		FileFilter chosenFileFilter = dfcp.getChosenFileFilter();

		String path = "";

		if( defaultFileName != null )
			path = ( new File( defaultFileName ) ).getParentFile().getAbsolutePath();
		else if( conf != null )
			path = conf.getLastDirectory();

		ZoomJFileChooser chooser=new ZoomJFileChooser(path, conf.getZoomFactor(), dfcp.getLocale() );

		Rectangle new100percentBounds = null;
		if( ( conf != null ) && ( conf.getLastFileChooserBounds() != null ) )
		{
			new100percentBounds = ViewFunctions.instance().calculateNewBoundsOnScreen( conf.getLastFileChooserBounds(), null, null, conf.getZoomFactor() );
		}
		chooser.init( parent, new100percentBounds );

		if( mode > 0 )
			chooser.setFileSelectionMode( mode );

		if( fileFilterList != null )
		{
			Iterator<FileFilter> it = fileFilterList.iterator();
			while( it.hasNext() )
			{
				FileFilter ff = it.next();
				chooser.addChoosableFileFilter(ff);
			}
		}

		if( chosenFileFilter != null )
			chooser.setFileFilter(chosenFileFilter);

		if( dfcp.getGenericFileFilter() != null )
			chooser.addChoosableFileFilter( dfcp.getGenericFileFilter() );

		M_changeFontToApplicationFontSize_forComponent( conf.getZoomFactor(), chooser.getRootPane().getContentPane() );
/*
		if( ( conf != null ) && ( conf.getLastFileChooserBounds() != null ) )
		{
			Rectangle newBounds = ViewFunctions.instance().calculateNewBoundsOnScreen( conf.getLastFileChooserBounds(), null, null, conf.getZoomFactor() );
//			chooser.setDialogBounds( newBounds );
		}
*/
		int returnVal = -1;

		if( defaultFileName != null )
			chooser.setSelectedFile( new File( defaultFileName ) );

		if( open_save_dialog == OPEN )		returnVal = chooser.showOpenDialog(parent);
		else if( open_save_dialog == SAVE )	returnVal = chooser.showSaveDialog(parent);

		chooser.addChoosableFileFilter( null );
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			path=chooser.getSelectedFile().getParentFile().getAbsolutePath();
			result = chooser.getSelectedFile().toString();

			if( conf != null ) conf.setLastDirectory(path );
		}

		if( conf != null )
		{
			Rectangle newBounds = ViewFunctions.instance().calculateNewBoundsOnScreen( chooser.getDialogBounds(), null, null, 1/conf.getZoomFactor() );
			conf.setLastFileChooserBounds( newBounds );
		}

		return( result );
	}

	public Point getCenteredLocationForComponent( Component comp )
	{
		int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		Point result = new Point( width/2 - comp.getWidth()/2, height/2 - comp.getHeight()/2 );

		return( result );
	}

	protected Font M_getNewFontForComponentFromApplicationFontSize( Component comp, double factor )
	{
		Font result = null;

		Font oldFont = comp.getFont();
		if( oldFont != null )
		{
			if( factor > 0.0F )
			{
				result = oldFont.deriveFont( (float) ( factor * oldFont.getSize2D() ) );
			}
		}

		return( result );
	}

	protected static class ZoomJFileChooser extends JFileChooser
	{
		protected JDialog _jDialog = null;

		protected Rectangle _initialBounds = null;

//		protected boolean _firstTime = true;

		public ZoomJFileChooser( String path, double zoomFactor )
		{
			this( path, zoomFactor, null );
		}

		public ZoomJFileChooser( String path, double zoomFactor, Locale locale )
		{
			super( path );

			System.out.println( "Locale: " + locale );

			if( locale == null )
				locale = Locale.US;
			setLocale( locale );

			System.out.println( "Locale: " + locale );

			ZoomMetalFileChooserUI ui = new ZoomMetalFileChooserUI( this );
			ui.setZoomFactor(zoomFactor);

			setUI( ui );

			if(isAcceptAllFileFilterUsed()) {
				resetChoosableFileFilters();
				addChoosableFileFilter(getAcceptAllFileFilter());
			}
		}

		protected void init( Component parent, Rectangle initialBounds )
		{
			_jDialog = createDialog_internal( parent );
			setDialogBounds( initialBounds );
		}

		protected JDialog createDialog_internal( Component parent ) throws HeadlessException
		{
			JDialog result = super.createDialog(parent);

			result.setAlwaysOnTop(true);

			return( result );
		}

		public JDialog getDialog()
		{
			return( _jDialog );
		}

		@Override
		public void setBounds( int xx, int yy, int width, int height )
		{
			super.setBounds( xx, yy, width, height );
		}

		// https://stackoverflow.com/questions/2270690/set-the-location-of-the-jfilechooser
		@Override
		protected JDialog createDialog(Component parent) throws HeadlessException
		{
			return( getDialog() );
		}

		public void setDialogBounds( Rectangle bounds )
		{
			_initialBounds = bounds;

			if( _initialBounds != null )
				_jDialog.setBounds( _initialBounds );
		}

		public Rectangle getDialogBounds()
		{
			Rectangle result = null;
			if( _jDialog != null )
			{
				result = _jDialog.getBounds();
			}
			return( result );
		}

		// https://www.google.com/search?q=jfilechooser+getComputerNodeFolder&rlz=1C1NCHA_enES646ES646&oq=jfilechooser+getComputerNodeFolder&aqs=chrome..69i57j33.4173j0j8&sourceid=chrome&ie=UTF-8
		@Override
		public void approveSelection() {
			File selectedFile = getSelectedFile();
			if (selectedFile != null && ShellFolder.isComputerNode(selectedFile)) {
				try {
					// Resolve path and try to navigate to it
					setCurrentDirectory(getComputerNodeFolder(selectedFile.getPath()));
				} catch (Exception ex) {
					// Alert user if given computer node cannot be accessed
					JOptionPane.showMessageDialog(this, "Cannot access " + selectedFile.getPath());
				}
			} else {
				super.approveSelection();
			}
		}

		protected File getComputerNodeFolder( String childFolderName )
		{
			File result = new File(childFolderName);
			FileSystemView fsv = FileSystemView.getFileSystemView();
			result = fsv.getParentDirectory(result);

			return( result );
		}
/*
		@Override
		public void paintComponent( Graphics grp )
		{
			super.paintComponent( grp );

			if( _firstTime )
			{
				_firstTime = false;
				System.out.println( String.format( "%n%n%nVisible%n=======%n" ) +
									ViewFunctions.instance().traceComponentTree(this, (component) -> "preferredSize : " + component.getPreferredSize() + "     size : " + component.getSize() ));
			}
		}
*/
	}
}
