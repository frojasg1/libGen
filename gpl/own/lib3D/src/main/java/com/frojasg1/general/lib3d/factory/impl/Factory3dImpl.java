/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.lib3d.factory.impl;

import com.frojasg1.general.lib3d.components.Canvas3dJPanel;
import com.frojasg1.general.lib3d.factory.Factory3dApi;
import com.frojasg1.general.lib3d.scenarios.Scenario3dBase;
import com.frojasg1.general.lib3d.scenarios.api.Scenario3dApi;
import com.frojasg1.general.lib3d.scenarios.impl.TorusScenario;
import java.awt.Color;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class Factory3dImpl implements Factory3dApi
{

	@Override
	public TorusScenario createTorus(float majorRadius, float minorRadius, int majorSamples, int minorSamples, Color color) {
		TorusScenario result = new TorusScenario();
		result.init( majorRadius, minorRadius, majorSamples, minorSamples, color );

		return( result );
	}

	@Override
	public Canvas3dJPanel createCanvas3dJPanel(Scenario3dApi scenario) {
		Canvas3dJPanel result = null;

		if( scenario instanceof Scenario3dBase )
		{
			result = new Canvas3dJPanel();

			result.init( (Scenario3dBase) scenario );
		}
		else
		{
			throw( new IllegalArgumentException( "scenario was not an instance of Scenario3dBase. cannot create Canvas3dJPanel" ) );
		}

		return( result );
	}
}
