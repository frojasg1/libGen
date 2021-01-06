/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.generic.application;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface ApplicationFacilitiesInterface {
	public String getApplicationVersion();
	public String buildResourceCounterUrl( String realUrl );
}
