/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.xml.persistency.container;

import com.frojasg1.applications.common.configuration.InternationalizedStringConf;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.xml.model.KeyModel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class SimpleMapContainerOfModelsKeyString<MM extends KeyModel<String> >
			extends SimpleMapContainerOfModels<String, MM>
			implements InternationalizedStringConf
{
	@Override
	public void init( SimpleMapContainerOfModels<String, MM> that )
	{
		throw( new RuntimeException( "Non usable init function" ) );
	}

	public void init( BaseApplicationConfigurationInterface appliConf,
						String languageGlobalConfFileName,
						String languagePropertiesFolderInJar,
						Function<String, String> fileNameCreatorFunction )
	{
		super.init( appliConf, languageGlobalConfFileName,
					languagePropertiesFolderInJar,
					fileNameCreatorFunction );
	}

	public void init( SimpleMapContainerOfModelsKeyString<MM> that )
	{
		super.init( that );
	}

	protected <KKK, VVV> Map<KKK, VVV> createMap()
	{
		return( new HashMap<>() );
	}

	@Override
	public String getRelativeFileNameFromItemList( String item )
	{
		return( createRelativeFileName(item) );
	}
}
