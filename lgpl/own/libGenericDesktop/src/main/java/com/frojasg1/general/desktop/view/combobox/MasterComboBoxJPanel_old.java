/* 
 * Copyright (C) 2021 Francisco Javier Rojas Garrido <frojasg1@hotmail.com>
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
package com.frojasg1.general.desktop.view.combobox;

import com.frojasg1.applications.common.components.resizecomp.MapResizeRelocateComponentItem;
import com.frojasg1.applications.common.components.resizecomp.ResizeRelocateItem;
//import com.frojasg1.general.desktop.combohistory.impl.TextComboBoxHistoryList;
import com.frojasg1.general.desktop.view.zoom.mapper.ComponentMapper;
import com.frojasg1.general.desktop.view.zoom.mapper.InternallyMappedComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import com.frojasg1.general.combohistory.TextComboBoxContent;

/**
 *
 * @author fjavier.rojas
 */
public abstract class MasterComboBoxJPanel_old extends com.frojasg1.general.desktop.view.panels.CustomJPanel implements InternallyMappedComponent
//public class MasterComboBoxJPanel_old extends com.frojasg1.general.desktop.view.panels.CustomJPanel implements InternallyMappedComponent
{
/*
	protected AddRemoveModifyItemNewSelectionController _controller = null;
	protected TextComboBoxContent _cbContents = null;

	protected MapResizeRelocateComponentItem _mapRRCI = null;

	protected ActionListener _actionListener = null;

	protected ControllerInvoker<String> _addInvoker = ( cont, result ) -> cont.added( this, result);
	protected ControllerInvoker<String> _removeInvoker = ( cont, result ) -> cont.removed( this, result);
	protected ControllerInvoker<String> _modifyInvoker = ( cont, result ) -> { cont.modify( this, result); return( null ); };

	public MasterComboBoxJPanel_old( AddRemoveModifyItemNewSelectionController controller,
								TextComboBoxHistoryList cbHistory )
	{
		initComponents();

		_controller = controller;
		_cbContents = cbHistory;
	}

	public void init()
	{
		addListeners();

		addCombo( getCombo() );

		_mapRRCI = createResizeRelocateInfo();
		_cbContents.addSelectionChangedListener((sender, prevItem, newItem) -> { if( _controller != null )	_controller.comboBoxSelectionChanged( this, prevItem, newItem );	});
	}

	public TextComboBoxHistoryList getHistory()
	{
		return( _cbContents );
	}

	protected ActionListener createActionListener()
	{
		return( evt -> actionPerformed( evt ) );
	}

	protected void addListeners()
	{
		_actionListener = createActionListener();

		jB_add.addActionListener( _actionListener );
		jB_remove.addActionListener( _actionListener );
		jB_modify.addActionListener( _actionListener );
	}

	protected JButton getAddButton()
	{
		return( jB_add );
	}

	protected JButton getRemoveButton()
	{
		return( jB_remove );
	}

	protected JButton getModifyButton()
	{
		return( jB_modify );
	}

	public JComboBox<String> getCombo()
	{
		return( jComboBox1 );
	}

	public MapResizeRelocateComponentItem getResizeRelocateInfo()
	{
		if( _mapRRCI == null )
		{
			_mapRRCI = createResizeRelocateInfo();
		}

		return( _mapRRCI );
	}

	protected MapResizeRelocateComponentItem createResizeRelocateInfo()
	{
		MapResizeRelocateComponentItem mapRRCI = new MapResizeRelocateComponentItem();

		try
		{
			mapRRCI.putResizeRelocateComponentItem( jPanel1, ResizeRelocateItem.FILL_WHOLE_PARENT );
			mapRRCI.putResizeRelocateComponentItem( jComboBox1, ResizeRelocateItem.RESIZE_TO_RIGHT );
			mapRRCI.putResizeRelocateComponentItem( jB_add, ResizeRelocateItem.MOVE_TO_RIGHT );
			mapRRCI.putResizeRelocateComponentItem( jB_remove, ResizeRelocateItem.MOVE_TO_RIGHT );
			mapRRCI.putResizeRelocateComponentItem( jB_modify, ResizeRelocateItem.MOVE_TO_RIGHT );

//			HintForComponent hfc = new HintForComponent( jB_acceptChanges, "Accept changes" );
//			HintForComponent hfc2 = new HintForComponent( jB_eraseTag, "Erase record" );
//			HintForComponent hfc3 = new HintForComponent( jB_revertChanges, "Revert changes" );
		}
		catch( Throwable th )
		{
			th.printStackTrace();
		}

		return( mapRRCI );
	}
*/
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jB_remove = new javax.swing.JButton();
        jB_add = new javax.swing.JButton();
        jB_modify = new javax.swing.JButton();

        setLayout(null);

        jPanel1.setLayout(null);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(10, 10, 150, 20);

        jB_remove.setMaximumSize(new java.awt.Dimension(20, 23));
        jB_remove.setMinimumSize(new java.awt.Dimension(20, 23));
        jB_remove.setName("name=jB_remove,icon=com/frojasg1/generic/resources/addremove/remove.png"); // NOI18N
        jB_remove.setPreferredSize(new java.awt.Dimension(20, 23));
        jPanel1.add(jB_remove);
        jB_remove.setBounds(190, 10, 20, 23);

        jB_add.setMaximumSize(new java.awt.Dimension(20, 23));
        jB_add.setMinimumSize(new java.awt.Dimension(20, 23));
        jB_add.setName("name=jB_add,icon=com/frojasg1/generic/resources/addremove/add.png"); // NOI18N
        jB_add.setPreferredSize(new java.awt.Dimension(20, 23));
        jPanel1.add(jB_add);
        jB_add.setBounds(167, 10, 20, 23);

        jB_modify.setMaximumSize(new java.awt.Dimension(20, 23));
        jB_modify.setMinimumSize(new java.awt.Dimension(20, 23));
        jB_modify.setName("name=jB_modify,icon=com/frojasg1/generic/resources/addremove/modify.png"); // NOI18N
        jB_modify.setOpaque(false);
        jB_modify.setPreferredSize(new java.awt.Dimension(20, 23));
        jPanel1.add(jB_modify);
        jB_modify.setBounds(213, 10, 20, 23);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 240, 40);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_add;
    private javax.swing.JButton jB_modify;
    private javax.swing.JButton jB_remove;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
/*
	protected void removeListeners()
	{
		jB_add.removeActionListener(_actionListener);
		jB_remove.removeActionListener(_actionListener);
		jB_modify.removeActionListener(_actionListener);
	}

	public void addCombo( JComboBox combo )
	{
		if( _cbContents != null )
			_cbContents.addCombo(combo);
	}

	public void dispose()
	{
		removeListeners();

		if( _cbContents != null )
			_cbContents.dispose();
	}

	protected void actionPerformed( ActionEvent evt )
	{
		if( evt.getSource() == jB_add )
			addEvent();
		else if( evt.getSource() == jB_remove )
			removeEvent();
		else if( evt.getSource() == jB_modify )
			modifyEvent();
	}

	protected AddRemoveModifyItemResult<String> createAddRemoveItemResult()
	{
		return( new AddRemoveModifyItemResultImpl<>() );
	}

	protected String invokeController( String item, ControllerInvoker<String> invoker )
	{
		String result = item;

		if( _controller != null )
		{
			AddRemoveModifyItemResult<String> ariResult = createAddRemoveItemResult();
			ariResult.setItem(item);

			result = invoker.invoke( _controller, ariResult);
		}

		return( result );
	}

	protected String getItemToAdd()
	{
		return( invokeController( getSelectedItem(), _addInvoker ) );
	}

	protected void addEvent()
	{
		String newAddedItem = getItemToAdd();

		addNewItem( newAddedItem );
	}

	protected String getItemToRemove()
	{
		return( invokeController( getSelectedItem(), _removeInvoker ) );
	}

	protected void removeEvent()
	{
		String itemToRemove = getItemToRemove();

		removeItem( itemToRemove );
	}

	protected void modifyEvent()
	{
		invokeController( getSelectedItem(), _modifyInvoker );
	}

	protected String getSelectedItem()
	{
		return( (String) jComboBox1.getSelectedItem() );
	}

	public void save()
	{
		if( _cbContents != null )
			_cbContents.save();
	}

	public void addNewItem( String item )
	{
		if( ( _cbContents != null ) && ( item != null ) )
			_cbContents.newItemSelected(item);
	}

	public void removeItem( String item )
	{
		if( _cbContents != null )
			_cbContents.removeItem(item);
	}

	@Override
	public void setComponentMapper(ComponentMapper compMapper) {
		jB_add = compMapper.mapComponent( jB_add );
		jB_modify = compMapper.mapComponent( jB_modify );
		jB_remove = compMapper.mapComponent( jB_remove );
		jComboBox1 = compMapper.mapComponent( jComboBox1 );
		jPanel1 = compMapper.mapComponent( jPanel1 );
	}

	protected interface ControllerInvoker<CC>
	{
		public CC invoke( AddRemoveModifyItemNewSelectionController<CC> controller, AddRemoveModifyItemResult<CC> result );
	}
*/
}
