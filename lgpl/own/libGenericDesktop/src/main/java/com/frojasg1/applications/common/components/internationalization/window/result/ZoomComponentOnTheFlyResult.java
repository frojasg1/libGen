/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.applications.common.components.internationalization.window.result;

import com.frojasg1.applications.common.components.internationalization.ExtendedZoomSemaphore;
import com.frojasg1.applications.common.components.resizecomp.MapResizeRelocateComponentItem;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ZoomComponentOnTheFlyResult {
	protected MapResizeRelocateComponentItem _resizeRelocateComponentItemMap;
	protected ExtendedZoomSemaphore _extendedZoomSemaphore;
	
	public void setMapResizeRelocateComponentItem( MapResizeRelocateComponentItem map )
	{
		_resizeRelocateComponentItemMap = map;
	}

	public MapResizeRelocateComponentItem getMapResizeRelocateComponentItem()
	{
		return( _resizeRelocateComponentItemMap );
	}

	public void setExtendedZoomSemaphore( ExtendedZoomSemaphore extendedZoomSemaphore )
	{
		_extendedZoomSemaphore = extendedZoomSemaphore;
	}

	public ExtendedZoomSemaphore getExtendedZoomSemaphore()
	{
		return( _extendedZoomSemaphore );
	}
}
