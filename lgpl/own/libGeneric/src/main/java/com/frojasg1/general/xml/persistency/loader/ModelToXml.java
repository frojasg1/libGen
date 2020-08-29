/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.xml.persistency.loader;

import com.frojasg1.general.CollectionFunctions;
import com.frojasg1.general.xml.XmlElement;
import com.frojasg1.general.xml.XmlFunctions;
import java.util.List;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface ModelToXml<CC> {

	public XmlElement build( CC model );
}
