/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.applications.common.components.internationalization.window;

import com.frojasg1.applications.common.components.internationalization.window.exceptions.ValidationException;
import java.awt.Component;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface GenericValidator {

	public void validation( Supplier<String> validationFunction,
							Component comp,
							Function<String, String> errorMessageCreatorFunction )
		throws ValidationException;
}
