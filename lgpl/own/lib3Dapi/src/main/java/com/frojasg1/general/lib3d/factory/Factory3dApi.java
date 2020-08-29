/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.lib3d.factory;

import com.frojasg1.general.lib3d.components.api.Canvas3dJPanelApi;
import com.frojasg1.general.lib3d.scenarios.api.Scenario3dApi;
import java.awt.Color;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public interface Factory3dApi {
	
	public Scenario3dApi createTorus(float majorRadius, float minorRadius,
										int majorSamples, int minorSamples,
										Color color);

	public Canvas3dJPanelApi createCanvas3dJPanel( Scenario3dApi scenario );
}
