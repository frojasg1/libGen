/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.view;

import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JSpinner;
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
}
