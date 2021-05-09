/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view.color.renderers;

import com.frojasg1.general.ClassFunctions;
import com.frojasg1.general.NullFunctions;
import com.frojasg1.general.desktop.view.color.ColorInversor;
import com.frojasg1.general.desktop.view.combobox.renderer.ComboCellRendererBase;
import java.awt.Color;
import java.awt.Component;
import java.util.Objects;
import java.util.function.Function;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ListCellRendererColorInversor implements ListCellRenderer
{
	protected ListCellRenderer _originalRenderer;
	protected ColorInversor _colorInversor;

	public ListCellRendererColorInversor( ListCellRenderer originalRenderer,
										ColorInversor colorInversor )
	{
		_originalRenderer = originalRenderer;
		_colorInversor = colorInversor;
//		invertOriginalBackground();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
												int index, boolean isSelected,
												boolean cellHasFocus)
	{
		Component result = _originalRenderer.getListCellRendererComponent( list, value,
														index, isSelected, cellHasFocus );

//		_colorInversor.invertSingleColorsGen(result);

		if( isSelected || hasBeenChangedBackground( result.getBackground() ))
		{
			_colorInversor.invertBackground(result);
		}

		if( isSelected || hasBeenChangedForeground( result.getForeground() ))
		{
			_colorInversor.invertForeground(result);
		}

		return( result );
	}

	protected boolean hasBeenChangedBackground( Color color )
	{
		return( !equalsOriginalColor( color, ComboCellRendererBase::getOriginalBackground ) );
	}

	protected boolean hasBeenChangedForeground( Color color )
	{
		return( !equalsOriginalColor( color, ComboCellRendererBase::getOriginalForeground ) );
	}

	protected boolean equalsOriginalColor( Color color, Function<ComboCellRendererBase, Color> getter )
	{
		Color originalColor = NullFunctions.instance().getIfNotNull(
			ClassFunctions.instance().cast(_originalRenderer, ComboCellRendererBase.class), getter );

		return( ( originalColor == null ) || Objects.equals( originalColor, color ) );
	}

	public void invertOriginalBackground()
	{
		_colorInversor.invertBackground( _originalRenderer );
	}

	public ListCellRenderer getOriginalListCellRenderer()
	{
		return( _originalRenderer );
	}
}
