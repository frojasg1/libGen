/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.functional.interfaces;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
@FunctionalInterface
public interface SupplierWithException<CC>
{
	public CC get() throws Exception;
}