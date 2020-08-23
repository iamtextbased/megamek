/*
 * Copyright (c) 2020 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.client.ui.swing.gameConnectionDialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import megamek.client.ui.Messages;

/** The Connect to game (as Bot or Player) dialog */
public class ConnectDialog extends AbstractGameConnectionDialog {
    private static final long serialVersionUID = 5895056240077042429L;

    private String serverAddress;
    private JTextField serverAddressField;

    public ConnectDialog(JFrame frame) {
        super(frame, Messages.getString("MegaMek.ConnectDialog.title"), true);
    }

    //region Initialization
    @Override
    protected void initComponents() {
        JLabel yourNameL = new JLabel(Messages.getString("MegaMek.yourNameL"), SwingConstants.RIGHT);
        JLabel serverAddrL = new JLabel(Messages.getString("MegaMek.serverAddrL"), SwingConstants.RIGHT);
        JLabel portL = new JLabel(Messages.getString("MegaMek.portL"), SwingConstants.RIGHT);
        setPlayerNameField(new JTextField(getClientPreferences().getLastPlayerName(), 16));
        getPlayerNameField().addActionListener(this);
        serverAddressField = new JTextField(getClientPreferences().getLastConnectAddr(), 16);
        serverAddressField.addActionListener(this);
        setPortField(new JTextField(getClientPreferences().getLastConnectPort() + "", 4));
        getPortField().addActionListener(this);
        
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        
        addOptionRow(middlePanel, c, yourNameL, getPlayerNameField());
        addOptionRow(middlePanel, c, serverAddrL, serverAddressField);
        addOptionRow(middlePanel, c, portL, getPortField());
        
        add(middlePanel, BorderLayout.CENTER);
        
        createButtons();
        
        pack();
        setResizable(false);
        center();
    }
    //endregion Initialization

    //region Getters and Setters
    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
    //endregion Getters and Setters

    //region Validation
    @Override
    public boolean dataValidation(String errorTitleKey, String errorMessageKey) {
        return super.dataValidation(errorTitleKey, errorMessageKey) && (getServerAddress() != null);
    }
    //endregion Validation

    @Override
    public void actionPerformed(ActionEvent e) {
        // reached from the Okay button or pressing Enter in the text fields
        super.actionPerformed(e);
        setServerAddress(serverAddressField.getText());

        // update settings
        getClientPreferences().setLastConnectAddr(getServerAddress());
        setVisible(false);
    }
}
