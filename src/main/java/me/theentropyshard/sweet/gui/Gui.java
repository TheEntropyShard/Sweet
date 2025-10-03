/*
 * Sweet - https://github.com/TheEntropyShard/Sweet
 * Copyright (C) 2025 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.sweet.gui;

import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import me.theentropyshard.sweet.Sweet;
import me.theentropyshard.sweet.api.GatewayEventAdapter;
import me.theentropyshard.sweet.api.LoginCallback;
import me.theentropyshard.sweet.api.ShootClient;
import me.theentropyshard.sweet.api.model.Guild;
import me.theentropyshard.sweet.api.model.event.server.ServerReadyEvent;
import me.theentropyshard.sweet.gui.components.CardLayoutPanel;
import me.theentropyshard.sweet.gui.laf.LightSweetLaf;
import me.theentropyshard.sweet.gui.view.MainView;
import me.theentropyshard.sweet.gui.view.guild.GuildListItem;
import me.theentropyshard.sweet.gui.view.login.LoginView;

public class Gui {
    private final CardLayoutPanel root;

    public Gui(Runnable onWindowClosing) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);

        FlatLaf.registerCustomDefaultsSource("themes");

        LightSweetLaf.setup();

        this.root = new CardLayoutPanel();
        this.root.setPreferredSize(new Dimension(1280, 720));

        LoginView loginView = new LoginView((instance, username, password) -> {
            ShootClient client = Sweet.getInstance().getShootClient();

            client.setInstance(instance);

            client.login(
                username, password, new LoginCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                }
            );
        });

        this.root.add(loginView, "login");

        MainView mainView = new MainView(this, () -> {});
        this.root.add(mainView, "main");

        JFrame frame = new JFrame("Sweet");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing.run();
            }
        });
        frame.add(this.root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showLogin() {
        this.root.show("login");
    }

    public void showMain() {
        this.root.show("main");
    }
}
