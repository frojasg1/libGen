/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.xml.persistency.container;

import com.frojasg1.applications.common.configuration.InternationalizedStringConf;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.applications.common.configuration.imp.InternationalizedStringConfImp;
import com.frojasg1.general.xml.model.KeyModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class SimpleMapContainerOfModels<KK, MM extends KeyModel<KK> > extends ContainerOfModels<KK, MM>
												implements InternationalizedStringConf
{
	protected static final String CONF_ALREADY_EXISTED = "ALREADY_EXISTED";

	protected String _languageGlobalConfFileName = null;
	protected String _languagePropertiesFolderInJar = null;

	protected InternationalizedStringConfImp _internationalizedStringConf = null;

	protected Map< KK, MM > _map = null;

	@Override
	public void init( ContainerOfModels that )
	{
		throw( new RuntimeException( "Non usable init function" ) );
	}

	public void init( BaseApplicationConfigurationInterface appliConf,
						Function<KK, String> fileNameCreatorFunction )
	{
		throw( new RuntimeException( "Non usable init function" ) );
	}

	public void init( BaseApplicationConfigurationInterface appliConf,
						String languageGlobalConfFileName,
						String languagePropertiesFolderInJar,
						Function<KK, String> fileNameCreatorFunction )
	{
		super.init( appliConf, fileNameCreatorFunction );

		_languageGlobalConfFileName = languageGlobalConfFileName;
		_languagePropertiesFolderInJar = languagePropertiesFolderInJar;

		_internationalizedStringConf = new InternationalizedStringConfImp( languageGlobalConfFileName,
								languagePropertiesFolderInJar );

		registerInternationalizedStrings();

		_map = createMap();
	}

	protected <KKK, VVV> Map<KKK, VVV> createMap()
	{
		return( new HashMap<>() );
	}

	// function for DefaultConstructorInitCopier
	public void init( SimpleMapContainerOfModels<KK, MM> that )
	{
		super.init( that );

		_map = _copier.copyMap( that._map );
		_internationalizedStringConf = that._internationalizedStringConf;
	}

	public MM createAndAddEmptyFileModel( KK newKey )
	{
		if( get( newKey ) != null )
			throw( new RuntimeException( createCustomInternationalString( CONF_ALREADY_EXISTED, newKey ) ) );

		MM result = createModelObject();
		result.setKey(newKey);
		add( result );

		return( result );
	}

	@Override
	public void add( MM rwc )
	{
		if( rwc != null )
			_map.put( rwc.getKey(), rwc );
	}

	public MM get( KK key )
	{
		return( _map.get( key ) );
	}

	public MM remove( String key )
	{
		return( _map.remove( key ) );
	}

	public MM rename( KK oldKey, KK newKey )
	{
		MM model = _map.remove( oldKey );
		if( model != null )
		{
			model.setKey(newKey);
			add( model );
		}

		return( model );
	}

	@Override
	public Collection<MM> getCollectionOfModelItems()
	{
		return( _map.values() );
	}

	protected void registerInternationalizedStrings()
	{
		this.registerInternationalString(CONF_ALREADY_EXISTED, "$1 already existed" );
	}

	@Override
	public void registerInternationalString(String label, String value)
	{
		_internationalizedStringConf.registerInternationalString(label, value);
	}

	@Override
	public String getInternationalString(String label)
	{
		return( _internationalizedStringConf.getInternationalString(label ) );
	}

	@Override
	public String createCustomInternationalString(String label, Object... args) {
		return( _internationalizedStringConf.createCustomInternationalString( label, args ) );
	}

	@Override
	public void changeLanguage(String language) throws Exception {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
