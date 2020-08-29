/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.desktop.image.processing.utils;

/**
 *
 * @author Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
 */
public class ImageUtils
{
	protected static ImageUtils _instance = null;

	public static ImageUtils instance()
	{
		if( _instance == null )
			_instance = new ImageUtils();

		return( _instance );
	}


    public int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        //int gray = (r + g + b) / 3;

        return gray;
    }
}
