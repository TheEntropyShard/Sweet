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

package me.theentropyshard.sweet;

import me.theentropyshard.sweet.api.ShootClient;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;

import me.theentropyshard.sweet.gui.Gui;

public class Sweet {
    private static final Logger LOG = LogManager.getLogger(Sweet.class);

    private final OkHttpClient httpClient;
    private final ShootClient shootClient;

    private Gui gui;

    public Sweet() {
        Sweet.instance = this;

        this.httpClient = new OkHttpClient.Builder().build();

        this.shootClient = new ShootClient(this.httpClient);

        try {
            SwingUtilities.invokeAndWait(() -> {
                this.gui = new Gui(() -> {
                    this.shootClient.closeWebSocket();
                });
            });
        } catch (InterruptedException e) {
            Sweet.LOG.error("Could not wait for the GUI", e);
        } catch (InvocationTargetException e) {
            Sweet.LOG.error("An exception occurred while creating the GUI", e);
        }
    }

    private static Sweet instance;

    public static Sweet getInstance() {
        return Sweet.instance;
    }

    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    public ShootClient getShootClient() {
        return this.shootClient;
    }
}
