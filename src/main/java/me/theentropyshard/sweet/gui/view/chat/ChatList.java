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

package me.theentropyshard.sweet.gui.view.chat;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import me.theentropyshard.sweet.gui.FlatSmoothScrollPaneUI;

public class ChatList extends JPanel {
    private final Map<String, ChatListItem> items;
    private final JPanel chatsPanel;

    public ChatList() {
        this.items = new HashMap<>();

        this.chatsPanel = new JPanel();
        this.chatsPanel.setLayout(new MigLayout("fillx, flowy"));

        this.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(
            this.chatsPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setUI(new FlatSmoothScrollPaneUI());

        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void removeAll() {
        this.items.clear();
        this.chatsPanel.removeAll();
    }

    public void addChat(ChatListItem item) {
        this.items.put(item.getRawName(), item);
        this.chatsPanel.add(item, "width 200px");
    }
}
