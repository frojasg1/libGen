/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.completion.base;

import com.frojasg1.general.completion.PrototypeManagerBase;
import com.frojasg1.applications.common.configuration.InternationalStringsConfiguration;
import com.frojasg1.applications.common.configuration.InternationalizedStringConf;
import com.frojasg1.applications.common.configuration.application.BaseApplicationConfigurationInterface;
import com.frojasg1.general.desktop.completion.InputTextComponentListener;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public abstract class WholeCompletionManagerBase
{
	protected InputTextComponentListener _inputTextListener = null;

	protected PrototypeManagerBase _prototypeManager = null;

	protected DesktopCompletionWindowBase _completionWindow = null;

	protected InputTextCompletionManagerBase< Rectangle > _inputTextCompletionManager = null;

	protected BaseApplicationConfigurationInterface _appliConf = null;

	protected InternationalizedStringConf _translatorOfType = null;

	public void init( BaseApplicationConfigurationInterface appliConf )
	{
		_appliConf = appliConf;

		_prototypeManager = createPrototypeManager();
//		_completionWindow = createCompletionWindow();
		_inputTextListener = createInputTextComponentListener();
//		_inputTextCompletionManager = createAndInitDesktopInputTextCompletionManager();

//		_inputTextCompletionManager.setPrototypeManager(_prototypeManager);

		_translatorOfType = createTranslatorOfType();

//		_inputTextListener.setCompletionManager(_inputTextCompletionManager);
	}

	protected InputTextCompletionManagerBase< Rectangle > getInputTextCompletionManagerBase()
	{
		return( _inputTextCompletionManager );
	}

	protected PrototypeManagerBase getPrototypeManager()
	{
		return( _prototypeManager );
	}

	protected abstract InputTextCompletionManagerBase< Rectangle > createDesktopInputTextCompletionManager();

	protected InputTextCompletionManagerBase< Rectangle > createAndInitDesktopInputTextCompletionManager()
	{
		InputTextCompletionManagerBase< Rectangle > result = createDesktopInputTextCompletionManager();

		result.setPrototypeManager(_prototypeManager);
		_inputTextListener.setCompletionManager(result);

		return( result );
	}

	protected InputTextComponentListener createInputTextComponentListener()
	{
		return( new InputTextComponentListener() );
	}

	protected abstract InternationalStringsConfiguration createTranslatorOfType();
/*
	{
		return( new TranslatorOfTypeForCompletion( getAppliConf() ) );
	}
*/
	protected abstract DesktopCompletionWindowBase createCompletionWindow( Component compForNotHiding );
/*
	{
		DesktopCompletionWindowBase result = null;

		Component mainWindow = ComponentFunctions.instance().getAncestor( compForNotHiding );
		if( mainWindow instanceof JFrame )
			result = new DesktopCompletionWindowBase( (JFrame) mainWindow, getAppliConf(),
													_translatorOfType );
		else
		{
			JDialog dialog = null;
			if( mainWindow instanceof JDialog )
				dialog = (JDialog) mainWindow;

			result = new DesktopCompletionWindowBase( dialog, getAppliConf(),
													_translatorOfType );
		}

		result.setComponentForNotHiding( compForNotHiding );

		return( result );
	}
*/
	protected BaseApplicationConfigurationInterface getAppliConf()
	{
		return( _appliConf );
	}

	protected abstract PrototypeManagerBase createPrototypeManager();

	public void setInputTextCompletionManager( JTextComponent textcomp,
													Component compForNotHiding )
	{
/*		if( _regexWholeContainer != regexWholeContainer )
			setRegexConfigurations( regexWholeContainer );

		_regexWholeContainer = regexWholeContainer;
*/
		if( _completionWindow != null )
			_completionWindow.formWindowClosing(true);

		_inputTextCompletionManager = createAndInitDesktopInputTextCompletionManager();

		_completionWindow = createCompletionWindow( compForNotHiding );
		_completionWindow.setCompletionManager(_inputTextCompletionManager);
		_inputTextCompletionManager.setCompletionWindow(_completionWindow);

		_inputTextListener.setNewJTextComponent(textcomp);
		_inputTextCompletionManager.setInputTextComponent(_inputTextListener.getViewTextComponent());

	}

	public JTextComponent getTextComponent()
	{
		JTextComponent result = null;
		if( ( _inputTextListener != null ) && ( _inputTextListener.getViewTextComponent() != null ) )
			result = _inputTextListener.getViewTextComponent().getComponent();

		return( result );
	}

	public DesktopCompletionWindowBase getCompletionWindow()
	{
		return( _completionWindow );
	}
}
