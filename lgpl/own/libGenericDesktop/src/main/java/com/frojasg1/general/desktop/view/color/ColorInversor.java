/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.color;

import com.frojasg1.applications.common.components.data.ComponentData;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem;
import com.frojasg1.general.ExecutionFunctions;
import com.frojasg1.general.NullFunctions;
import com.frojasg1.general.desktop.image.ImageFunctions;
import com.frojasg1.general.desktop.image.ImageUtilFunctions;
import com.frojasg1.general.desktop.lookAndFeel.ToolTipLookAndFeel;
import com.frojasg1.general.desktop.view.ComponentFunctions;
import com.frojasg1.general.desktop.view.ComponentTranslator;
import com.frojasg1.general.desktop.view.FrameworkComponentFunctions;
import com.frojasg1.general.desktop.view.IconFunctions;
import com.frojasg1.general.desktop.view.color.renderers.ListCellRendererColorInversor;
import com.frojasg1.general.desktop.view.color.renderers.TableCellRendererColorInversor;
import com.frojasg1.general.desktop.view.color.uimanagers.impl.UIManagerParamColorsForInversionImpl;
import com.frojasg1.general.desktop.view.splitpane.SplitPaneFunctions;
import com.frojasg1.general.desktop.view.zoom.components.ZoomJComboBox;
import com.frojasg1.general.reflection.ReflectionFunctions;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSplitPaneUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ColorInversor
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ColorInversor.class);

	protected Map<Integer, Color> _inverseColorMap = new ConcurrentHashMap<>();

	protected Boolean _isDarkMode = null;


	public BufferedImage invertImage( BufferedImage image )
	{
		return( ImageFunctions.instance().invertImage(image) );
	}

	public BufferedImage putOutImageColor( BufferedImage image, double factor )
	{
		return( ImageFunctions.instance().translateImageColors(image,
				argb -> ImageUtilFunctions.instance().putOutColor(argb, factor) ) );
	}

	public Color[] putOutColors( double factor, Color ... colors )
	{
		return( translateColors( col -> putOutColor( factor, col ),	colors) );
	}

	public Color putOutColor( double factor, Color color )
	{
		return( new Color( ImageUtilFunctions.instance().putOutColor(color.getRGB(), factor), true ) );
	}

	public Color invertColor( Color color )
	{
		Color result = null;
		if( color != null )
		{
			int rgb = color.getRGB();
			result = _inverseColorMap.get( rgb );
			if( result == null )
			{
				result = new Color( ImageUtilFunctions.instance().invertColor( rgb ), true );
				_inverseColorMap.put( rgb, result );
			}
		}

		return( result );
	}

	protected void oneTimeTasks( boolean isDarkMode )
	{
		UIManagerParamColorsForInversionImpl.instance().setDarkMode(isDarkMode, this);
	}

	protected void filterToProcessChildren( Component comp,
									ComponentFunctions.ComponentProcessingResult compProcessResult,
									AtomicBoolean isFirst, boolean isDarkMode,
									Function<Component, ColorThemeChangeableStatus> colorThemeChangeableGetter2 )
	{
		if( comp instanceof ColorInversorOwner )
			compProcessResult.setProcessChildren( isFirst.get() );

		if( compProcessResult.hasToProcessChildren() )
			setSingleDarkMode( comp, isDarkMode, colorThemeChangeableGetter2 );

		isFirst.set(false);
	}

	public void setDarkMode( Component comp, boolean isDarkMode,
							Function<Component, ColorThemeChangeableStatus> colorThemeChangeableGetter )
	{
		oneTimeTasks(isDarkMode);
		Function<Component, ColorThemeChangeableStatus> colorThemeChangeableGetter2 =
			getColorThemeChangeableGetter( comp, colorThemeChangeableGetter );
		AtomicBoolean isFirst = new AtomicBoolean(true);
		ComponentFunctions.instance().browseComponentHierarchy(comp,
			(comp2, compProcessResult) -> filterToProcessChildren(
				comp2, compProcessResult, isFirst, isDarkMode,
				colorThemeChangeableGetter2 ) );
	}

	public void setOneShotInconditionalDarkMode( Component comp )
	{
		try
		{
			_isDarkMode = true;
			ComponentFunctions.instance().browseComponentHierarchy(comp,
							comp2 -> setInconditionalSingleDarkMode( comp2 ) );
		}
		finally
		{
			_isDarkMode = null;
		}
	}

	public void invertColor( Color color, Consumer<Color> setter )
	{
		if( ( color != null ) && ( setter != null ) )
		{
			Color invertedColor = invertColor( color );
			setter.accept(invertedColor);
		}
	}

	protected Color getAttributeColor( Object obj, String attributeName )
	{
		return( getFromAttributeGen( attributeName, Color.class, obj) );
	}

	protected Color getColor( Object obj, String colorGetterFunctionName )
	{
		return( getFromGetterGen(obj, colorGetterFunctionName) );
	}

	protected Icon getIcon( Object obj, String iconGetterFunctionName )
	{
		return( getFromGetterGen(obj, iconGetterFunctionName) );
	}

	protected <CC> CC getFromAttributeGen( String attributeName, Class<CC> returnClass, Object obj  )
	{
		return( (CC) ReflectionFunctions.instance().getAttribute(attributeName, returnClass, obj) );
	}

	protected <CC> CC getFromGetterGen( Object obj, String getterFunctionName )
	{
		return( (CC) ReflectionFunctions.instance().invokeMethod(getterFunctionName, obj) );
	}

	protected void invertAttributeColorGen( Object obj, String attributeName )
	{
		try
		{
			Color color = getAttributeColor( obj, attributeName );
			if( color != null )
			{
				Color invertedColor = invertColor( color );
				ReflectionFunctions.instance().setAttribute(attributeName, obj, obj.getClass(), invertedColor );
			}
		}
		catch( Exception ex )
		{
			LOGGER.info( "{} class does not have {} attribute or it cannot be accesed via reflection", obj.getClass(),
				attributeName );
		}
	}

	protected void invertColorsGen( Object obj, String getterName, String setterName )
	{
		try
		{
			Color color = getColor( obj, getterName );
			if( color != null )
				invertColor( color, c -> ReflectionFunctions.instance().invokeMethod(setterName, obj, c) );
		}
		catch( Exception ex )
		{
			LOGGER.info( "{} class does not have {} or {} functions", obj.getClass(),
				getterName, setterName );
		}
	}

	protected void invertIconColorsGen( Object obj, String getterName, String setterName )
	{
		try
		{
			Icon icon = getIcon( obj, getterName );
			ReflectionFunctions.instance().invokeMethod(setterName, obj, invertIconColors(icon) );
		}
		catch( Exception ex )
		{
			LOGGER.info( "{} class does not have {} or {} functions", obj.getClass(),
				getterName, setterName );
		}
	}

	protected Icon invertIconColors( Icon icon )
	{
		return( IconFunctions.instance().invertIconColors(icon) );
	}

	public void invertBackground( Object obj )
	{
		invertColorsGen( obj, "getBackground", "setBackground" );
	}

	public void invertForeground( Object obj )
	{
		invertColorsGen( obj, "getForeground", "setForeground" );
	}

	public void invertSingleColorsGen( Object obj )
	{
//		if( obj instanceof JToggleButton )
//			setColorInversionJToggleButtonActionListener( ( JToggleButton) obj );
//		else
//			invertColorsGen( obj, "getBackground", "setBackground" );
		invertBackground( obj );
		invertForeground( obj );

		invertColorsGen( obj, "getSelectionBackground", "setSelectionBackground" );
		invertColorsGen( obj, "getSelectionForeground", "setSelectionForeground" );
		invertColorsGen( obj, "getCaretColor", "setCaretColor" );
		invertColorsGen( obj, "getTitleColor", "setTitleColor" );	// for TitledBorder
		invertAttributeColorGen( obj, "lineColor" );	// for LineBorder

		if( !( obj instanceof ColorThemeInvertible ) )
		{
			invertIconColorsGen( obj, "getIcon", "setIcon" );
		}

		if( obj instanceof JComponent )
			invertBorderColors( obj );
	}
/*
	protected void setColorInversionJToggleButtonActionListener( JToggleButton tButton )
	{
		ActionPerformedForToggleButtonInvertColor actionListener = getActionPerformedForToggleButtonInvertColor(tButton);
		if( actionListener == null )
			setNewActionPerformedForToggleButtonInvertColor( tButton, isDarkMode(tButton) );
		else
			actionListener.setDarkMode( !actionListener.isDarkMode() );
	}

	protected ActionPerformedForToggleButtonInvertColor getActionPerformedForToggleButtonInvertColor( JToggleButton tButton)
	{
		ActionPerformedForToggleButtonInvertColor result = null;
		for( ActionListener listener: tButton.getActionListeners() )
			if( result instanceof ActionPerformedForToggleButtonInvertColor )
			{
				result = (ActionPerformedForToggleButtonInvertColor) listener;
				break;
			}
		return( result );
	}

	protected void setNewActionPerformedForToggleButtonInvertColor( JToggleButton tButton, boolean isDarkMode )
	{
		ActionPerformedForToggleButtonInvertColor actionListener = new ActionPerformedForToggleButtonInvertColor( tButton );
		tButton.addActionListener(actionListener);
		actionListener.setDarkMode( isDarkMode );
	}
*/
	protected boolean isDarkMode( Component comp )
	{
		return( ( _isDarkMode != null )
			? _isDarkMode
			: FrameworkComponentFunctions.instance().isDarkMode(comp) );
	}

	protected Border getBorder( Object obj )
	{
		return( getFromGetterGen( obj, "getBorder" ) );
	}

	protected void invertBorderColors( Object obj )
	{
		Border border = getBorder(obj);
		if( border != null )
		{
			TitledBorder brd;
			LineBorder brd2;
			invertSingleColorsGen(border);
			invertBorderColors(border);
		}
	}

	protected boolean isJTableChild( Component comp )
	{
//		return( ComponentFunctions.instance().getFirstParentInstanceOf(JTable.class, comp) != null );
		return( ( comp instanceof JTableHeader ) || ( comp instanceof CellRendererPane ) );
	}

	protected void invertSingleComponentColors( Component comp )
	{
		if( comp instanceof ColorThemeInvertible )
		{
			( (ColorThemeInvertible) comp ).invertColors(this);
		}
		else if( comp instanceof JScrollBar )
		{
			// intentionally left blank
		}
		else if( isJTableChild(comp) )
		{
			// intentionally left blank
		}
//		else if( comp instanceof CellPane )
//		{
//			// intentionally left blank
//		}
		else if( "ComboBox.list".equals( comp.getName() ) )
		{
			// intentionally left blank
		}
		else if( comp instanceof TableCellRenderer )
		{
			int kk=1;
			// intentionally left blank
		}
		else
		{
			if( comp instanceof JTable )
			{
				updateUi( comp );

				JTable table = (JTable) comp;
				invertRendererColors( table );
			}
			else
			{
				invertSingleColorsGen( comp );
				if( comp instanceof JComboBox )
				{
					JComboBox combo = (JComboBox) comp;
					invertRendererColors( combo );
					if( !( comp instanceof ZoomJComboBox) )
						invertIconColorsGen( combo, "getComboIcon", "setComboIcon" );		// for MetalComboBoxButton
				}
				else
					updateUi( comp );
			}
		}
	}

	protected <CC, RR> RR getIfNotNull( CC obj, Function<CC,RR> getter )
	{
		return( NullFunctions.instance().getIfNotNull(obj, getter) );
	}

	protected void updateZoom( Component comp )
	{
		ResizeRelocateItem rri = getIfNotNull( FrameworkComponentFunctions.instance().getComponentData(comp),
												ComponentData::getResizeRelocateItem );
		if( rri != null )
			SwingUtilities.invokeLater( () -> {
				rri.setForceExecution(true);
				rri.updateZoom();
				});
	}

	protected void updateUi( Component comp )
	{
		if( comp instanceof JProgressBar )
		{
			( (JProgressBar) comp ).updateUI();
			updateZoom(comp);
//			updateUi( (JProgressBar) comp );
		}
		else if( comp instanceof JButton )
		{
			if( !isDarkMode(comp) )
			{
				comp.setBackground(new ColorUIResource(0) );
				( (JButton) comp ).updateUI();
				updateZoom(comp);
			}
		}
//		else if( isDivider( comp ) && ( comp instanceof JComponent ) )
		else if( comp instanceof JSplitPane )
		{
			ToolTipLookAndFeel.instance().getTheme().setDarkMode( isDarkMode(comp) );
			( (JSplitPane) comp ).updateUI();
			ToolTipLookAndFeel.instance().getTheme().setDarkMode( false );
		}
//			updateUi( (JSplitPane) comp );
	}

	protected boolean isDivider( Component comp )
	{
		MetalSplitPaneUI ui;
		return( SplitPaneFunctions.instance().isDivider(comp) );
	}

	protected void updateUi( JComponent jcomp )
	{
		ComponentUI ui = ExecutionFunctions.instance().safeSilentFunctionExecution(
			() -> (ComponentUI) ReflectionFunctions.instance().invokeMethod( "getUI", jcomp ) );

//		ComponentUI newUi = createNewUi( jcomp, ui );
//		if( newUi != null )
		if( ui != null )
		{
			Font previousFont = jcomp.getFont();
//			jcomp.updateUI();
			ReflectionFunctions.instance().invokeMethod( "setUI", jcomp, ui );
			jcomp.setFont(previousFont);
		}
	}

	protected ComponentUI createNewUi( JComponent jcomp, ComponentUI ui )
	{
		ComponentUI result = null;

		if( ui != null )
			result = ExecutionFunctions.instance().safeFunctionExecution(
						() -> (ComponentUI) ReflectionFunctions.instance().invokeStaticMethod( "createUI", ui.getClass(), jcomp ) );

		return( result );
	}

	public void invertRendererColors( JComboBox combo )
	{
		ListCellRenderer renderer = combo.getRenderer();
		if( renderer instanceof ListCellRendererColorInversor )
		{
			ListCellRendererColorInversor lcrci = (ListCellRendererColorInversor) renderer;
//			lcrci.invertOriginalBackground();
			combo.setRenderer( lcrci.getOriginalListCellRenderer() );
		}
		else
			combo.setRenderer( createListCellRendererColorInversor(renderer) );
	}

	protected ListCellRenderer createListCellRendererColorInversor( ListCellRenderer originalCellRenderer )
	{
		return( new ListCellRendererColorInversor( originalCellRenderer, this ) );
	}

	public void invertRendererColors( JTable table )
	{
		Set<Object> alreadySet = new HashSet<>();

		JTableHeader jth = table.getTableHeader();
		if( jth != null )
		{
			TableCellRenderer origRenderer = jth.getDefaultRenderer();
			jth.setDefaultRenderer( getTableCellRendererColorInversor( origRenderer, "header", alreadySet  ) );
		}

		for( int jj=0; jj<table.getColumnCount(); jj++ )
		{
			Class<?> clazz = table.getColumnClass(jj);
			if( !alreadySet.contains(clazz) )
			{
				alreadySet.add(clazz);
				TableCellRenderer origRenderer = table.getCellRenderer(0, jj);
				table.setDefaultRenderer( clazz, getTableCellRendererColorInversor( origRenderer, "cells", alreadySet ) );
			}
//			invertTableCellRendererColors( table.getCellRenderer(0, jj), set);
//			if( table.getRowCount() > 1 )
//				invertTableCellRendererColors( table.getCellRenderer(1, jj), set);
		}
	}

	protected TableCellRenderer getTableCellRendererColorInversor( TableCellRenderer original,
																	String name,
																	Set<Object> alreadySet )
	{
		TableCellRenderer result = original;
		if( !alreadySet.contains( original ) )
		{
			result = tableCellRendererInvertColors(original, name);

			alreadySet.add( original );
			alreadySet.add( result );
		}

		return( result );
	}

	protected TableCellRenderer tableCellRendererInvertColors( TableCellRenderer original,
																String name)
	{
		TableCellRenderer result = original;
		if( original instanceof TableCellRendererColorInversor )
			result = ( (TableCellRendererColorInversor) original ).getOriginalTableCellRenderer();
		else
			result = createTableCellRendererColorInversor(original, name);

		return( result );
	}

	public TableCellRenderer createTableCellRendererColorInversor( TableCellRenderer original,
																	String name)
	{
		TableCellRendererColorInversor result = new TableCellRendererColorInversor( original, this );
		result.setName( name );
		return( result );
	}

/*
	protected void invertTableCellRendererColors( TableCellRenderer renderer, Set<Object> set)
	{
		if( !set.contains(renderer) )
		{
			set.add(renderer);
			invertSingleColorsGen( renderer );
		}
	}
*/
	public Component setSingleDarkMode( Component comp, boolean isDarkMode,
										Function<Component, ColorThemeChangeableStatus> colorThemeChangeableGetter )
	{
		if(comp.getParent() instanceof JComboBox )
		{
			int kk=1;
		}

		if( ( comp != null ) && ( colorThemeChangeableGetter != null ) &&
			!( //( comp instanceof BasicComboPopup ) ||
				(comp.getParent() instanceof JComboBox ) && ( comp instanceof CellRendererPane) ) )
		{
			ColorThemeChangeableStatus ctc = colorThemeChangeableGetter.apply(comp);
			if( ctc != null )
			{
				synchronized( ctc )
				{
					ctc.setDarkMode(isDarkMode, this);
					if( !( comp instanceof ColorThemeChangeableStatus ) &&
						( isDarkMode != ctc.wasLatestModeDark() ) &&
						!ctc.doNotInvertColors() )
					{
						invertSingleComponentColors( comp );
						ctc.setLatestWasDark(isDarkMode);
					}
				}
				SwingUtilities.invokeLater( () -> comp.repaint() );
			}
			else {
				LOGGER.warn( "ColorThemeChangeable not found for component: {}", comp );
			}
		}

		Component linkedComponent = null;
//		if( comp instanceof JSplitPane )
//			linkedComponent = SplitPaneFunctions.instance().getDivider( (JSplitPane) comp );

		return( linkedComponent );
//		return( null );
	}

	public Component setInconditionalSingleDarkMode( Component comp )
	{
		if( ( comp != null ) && 
			!( //( comp instanceof BasicComboPopup ) ||
				(comp.getParent() instanceof JComboBox ) && ( comp instanceof CellRendererPane) ) )
		{
			if( comp instanceof ColorThemeChangeableStatus )
				( ( ColorThemeChangeableStatus ) comp ).setDarkMode( true, this);
			else
			{
				if( comp instanceof ColorThemeChangeableStatusBuilder )
				{
					ColorThemeChangeableStatus ctc = ( (ColorThemeChangeableStatusBuilder) comp).createColorThemeChangeableStatus();
					ctc.setLatestWasDark(true);
					ctc.setDarkMode(true, this);
				}
/*
				if( comp instanceof JToggleButton )
					setNewActionPerformedForToggleButtonInvertColor((JToggleButton) comp, true);
*/
				invertSingleComponentColors( comp );
				
				if( ( comp instanceof JScrollBar ) &&
					"ComboBox.scrollPane".equals( comp.getParent().getName() ) )
				{
					this.invertSingleColorsGen(comp);
				}
			}

			SwingUtilities.invokeLater( () -> comp.repaint() );
		}
		return( null );
	}

	public Function<Component, ColorThemeChangeableStatus> getColorThemeChangeableGetter(
						Component comp, Function<Component, ColorThemeChangeableStatus> colorThemeChangeableGetter )
	{
		Function<Component, ColorThemeChangeableStatus> result = colorThemeChangeableGetter;
		if( result == null )
			result = getColorThemeChangeableGetterInternal( comp );

		return( result );
	}

	protected Function<Component, ColorThemeChangeableStatus> getColorThemeChangeableGetterInternal(
						Component comp )
	{
		return( ComponentTranslator.instance().getComponentTranslator(comp, ColorThemeChangeableStatus.class) );
	}

	public Color[] translateColors( Function<Color, Color> translator,
									Color ... colors ) {
		Color[] result = null;
		if( colors != null )
		{
			result = Arrays.copyOf( colors, colors.length );
		
			for( int ii=0; ii<result.length; ii++ )
				result[ii] = translator.apply(result[ii]);
		}

		return( result );
	}

	public Color[] invertColors( Color ... colors ) {
		return( translateColors( this::invertColor, colors ) );
	}

	public BufferedImage invertAndPutOutImage( BufferedImage image, double factor )
	{
		ImageUtilFunctions util = ImageUtilFunctions.instance();
		return( ImageFunctions.instance().translateImageColors(image,
			argb -> util.translateSimpleComponentsOfPixelARGB(argb,
					comp -> util.putOutComp(util.invertComponent(comp), factor) )
				)
			);
	}
/*
	protected class ActionPerformedForToggleButtonInvertColor implements ActionListener
	{
		protected JToggleButton _button;
		protected boolean _isDarkMode;
		protected Color _originalBackground;
		protected Color _nonSelectedBackgroundColor;

		public ActionPerformedForToggleButtonInvertColor( JToggleButton button )
		{
			_button = button;
			if( !_button.isSelected() )
			{
				_originalBackground = button.getBackground();
				_button.setSelected(true);
				_nonSelectedBackgroundColor = button.getBackground();
				_button.setSelected(false);
			}
			else
			{
				_nonSelectedBackgroundColor = button.getBackground();
				_button.setSelected(false);
				_originalBackground = button.getBackground();
				_button.setSelected(true);
			}
		}

		public void setDarkMode( boolean value )
		{
			if( _isDarkMode != value )
			{
				invertBackground();
				_originalBackground = invertColor( _originalBackground );
				_nonSelectedBackgroundColor = invertColor( _nonSelectedBackgroundColor);
//				if( _button.isSelected() )
//					updateBackground( _originalBackground );
			}
			_isDarkMode = value;
		}

		public boolean isDarkMode()
		{
			return( _isDarkMode );
		}

		// https://stackoverflow.com/questions/48213565/changing-to-color-of-a-jtogglebutton-only-when-the-button-is-selected-afterward
		@Override
		public void actionPerformed(ActionEvent e) {
			if( _button.isSelected() )
			{
				_nonSelectedBackgroundColor = _button.getBackground();
				if( isDarkMode() )
					invertBackground();
			}
			else if( _nonSelectedBackgroundColor != null )
				updateBackground( _nonSelectedBackgroundColor );
		}

		protected void invertBackground()
		{
			updateBackground( invertColor( _button.getBackground() ) );
		}

		protected void updateBackground( Color color )
		{
			_button.setBackground( color );
			_button.repaint();
		}
	}
*/
}
