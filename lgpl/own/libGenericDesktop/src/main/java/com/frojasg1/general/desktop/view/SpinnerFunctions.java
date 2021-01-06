/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view;

import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class SpinnerFunctions
{
	protected static SpinnerFunctions _instance = null;

	public static SpinnerFunctions instance()
	{
		if( _instance == null )
			_instance = new SpinnerFunctions();

		return( _instance );
	}

	public void browseSpinnerButtons( JSpinner spinner, Consumer<Component> consumer )
	{
		if( ( consumer != null ) && ( spinner.getUI() instanceof BasicSpinnerUI ) )
		{
			for (Component component : spinner.getComponents()) {
				if (component.getName() != null && component.getName().endsWith("Button")) {
					consumer.accept(component);
				}
			}
		}
	}

	public void setMaxValue( JSpinner spinner, Integer max )
	{
		modelSetterGen( spinner, (model) -> model.setMaximum(max) );
	}

	public void setMinValue( JSpinner spinner, Integer min )
	{
		modelSetterGen( spinner, (model) -> model.setMinimum(min) );
	}

	public void modelSetterGen( JSpinner spinner, Consumer<SpinnerNumberModel> setter )
	{
		SpinnerNumberModel model = getSpinnerNumberModel( spinner );
		if( model != null )
			setter.accept(model);
	}

	protected SpinnerNumberModel getSpinnerNumberModel( JSpinner spinner )
	{
		SpinnerNumberModel result = null;
		if( spinner.getModel() instanceof SpinnerNumberModel )
			result = (SpinnerNumberModel) spinner.getModel();

		return( result );
	}

	public void limitRange( JSpinner spinner, Integer min, Integer max )
	{
		if( spinner != null )
		{
			this.setMinValue(spinner, min);
			this.setMaxValue(spinner, max);
		}
	}
}
