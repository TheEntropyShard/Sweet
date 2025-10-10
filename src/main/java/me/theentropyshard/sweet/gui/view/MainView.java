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

package me.theentropyshard.sweet.gui.view;

import me.theentropyshard.sweet.gui.Gui;
import me.theentropyshard.sweet.gui.view.chat.ChatList;
import me.theentropyshard.sweet.gui.view.chat.ChatListHeader;
import me.theentropyshard.sweet.gui.view.chat.ChatView;
import me.theentropyshard.sweet.gui.view.guild.GuildList;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    private final Gui gui;

    private final GuildList guildList;
    private final JPanel centerPanel;
    private final ChatListHeader chatListHeader;
    private final ChatList chatList;
    private final ChatView chatView;

    private Runnable onPrivateClick;

    public MainView(Gui gui, Runnable onAddClick) {
        this.gui = gui;

        this.setLayout(new BorderLayout());

        this.guildList = new GuildList(() -> this.onPrivateClick.run(), onAddClick);
        this.add(this.guildList, BorderLayout.WEST);

        this.centerPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        this.centerPanel.add(leftPanel, BorderLayout.WEST);

        this.chatListHeader = new ChatListHeader();
        leftPanel.add(this.chatListHeader, BorderLayout.NORTH);

        this.chatList = new ChatList();
        leftPanel.add(this.chatList, BorderLayout.CENTER);

        this.chatView = new ChatView();
        this.centerPanel.add(this.chatView, BorderLayout.CENTER);

        this.add(this.centerPanel, BorderLayout.CENTER);

        new MainViewController(this);
    }

    public void setOnPrivateClick(Runnable onPrivateClick) {
        this.onPrivateClick = onPrivateClick;
    }

    public void showMain() {
        this.gui.showMain();
    }

    public GuildList getGuildList() {
        return this.guildList;
    }

    public ChatListHeader getChatListHeader() {
        return this.chatListHeader;
    }

    public ChatList getChatList() {
        return this.chatList;
    }

    public ChatView getChatView() {
        return this.chatView;
    }
}
