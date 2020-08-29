/* 
 * Copyright (C) 2020 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package com.frojasg1.general.dialogs;

import com.frojasg1.general.dialogs.filefilter.FilterForFile;
import com.frojasg1.applications.common.configuration.application.ConfigurationForFileChooserInterface;
import com.frojasg1.general.dialogs.filefilter.GenericFileFilterChooser;
import com.frojasg1.general.dialogs.filefilter.impl.GenericFileFilterChooserImpl;
import com.frojasg1.general.view.ViewComponent;
import com.frojasg1.general.zoom.ZoomInterface;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface DialogsWrapper
{
	// the next constants are for the integer parameter open_save_dialog of fileChooser
	public static final int OPEN = 0;
	public static final int SAVE = 1;

	// the next constants are for the integer parameter messageType
	public static final int ERROR_MESSAGE = 0;
	public static final int INFORMATION_MESSAGE = 1;
	public static final int PLAIN_MESSAGE = 2;
	public static final int QUESTION_MESSAGE = 3;
	public static final int WARNING_MESSAGE = 4;

	// the next constants are for the integer parameter optionType
	public static final int CANCEL_OPTION = 0;
	public static final int CLOSED_OPTION = 1;
	public static final int DEFAULT_OPTION = 2;
	public static final int OK_CANCEL_OPTION = 3;
	public static final int OK_OPTION = 4;
	public static final int YES_NO_CANCEL_OPTION = 5;
	public static final int YES_NO_OPTION = 6;
	public static final int YES_OPTION = 7;

	// following constants are refered to GenericFileFilter ids
	public static final int GENERIC_FILE_FILTER_FOR_EXECUTABLE_BY_ATTRIBUTES = GenericFileFilterChooserImpl.GENERIC_FILE_FILTER_FOR_EXECUTABLE_BY_ATTRIBUTES;

	/**
	 * 
	 * @param parent
	 * @param message
	 * @param title
	 * @param messageType    an example for this parameter: DialogsWrapper.ERROR_MESSAGE
	 */
	public void showMessageDialog( ViewComponent parent, Object message,
									String title, int messageType );
	public void showMessageDialog( ViewComponent parent, Object message,
									String title, int messageType,
									ZoomInterface conf );

	/**
	 * 
	 * @param parent
	 * @param message
	 * @param title
	 * @param optionType		an example for this parameter: DialogsWrapper.YES_NO_CANCEL_OPTION
	 * @param messageType		an example for this parameter: DialogsWrapper.QUESTION_MESSAGE
	 * @param options
	 * @param initialValue
	 * @return 
	 */
	public int showOptionDialog( ViewComponent parent, Object message, String title, int optionType,
										int messageType, Object[] options, Object initialValue );
	public int showOptionDialog( ViewComponent parent, Object message, String title, int optionType,
										int messageType, Object[] options, Object initialValue,
										ZoomInterface conf );

	/**
	 * 
	 * @param parent
	 * @param open_save_dialog	. This parameter has to contain either DialogsWrapper.OPEN or
								DialogsWrapper.SAVE
	 * @param fileFilterList
	 * @return 
	 */
	public String showFileChooserDialog( ViewComponent parent,
													int open_save_dialog,
													List<FilterForFile> fileFilterList,
													String defaultFileName );

	public String showFileChooserDialog( ViewComponent parent,
													int open_save_dialog,
													List<FilterForFile> fileFilterList,
													String defaultFileName,
													ConfigurationForFileChooserInterface conf );

	public String showFileChooserDialog( ViewComponent parent,
											FileChooserParameters pffc,
											ConfigurationForFileChooserInterface conf );

	public GenericFileFilterChooser getGenericFileFilterChooser();
}
