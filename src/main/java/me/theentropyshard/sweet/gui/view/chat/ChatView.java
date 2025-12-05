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

import me.theentropyshard.sweet.gui.FlatSmoothScrollPaneUI;

public class ChatView extends JPanel {
    private final JPanel messagesPanel;
    private final JScrollPane scrollPane;
    private final ChatInput chatInput;

    public ChatView() {
        this.messagesPanel = new JPanel();
        this.messagesPanel.setLayout(new MigLayout("insets 0 0 0 n, fillx, flowy"));

        this.scrollPane = new JScrollPane(
            this.messagesPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.scrollPane.setUI(new FlatSmoothScrollPaneUI());

        this.setLayout(new MigLayout("fill", "[]", "[center][bottom]"));

        this.add(this.scrollPane, "grow, wrap");
        this.chatInput = new ChatInput();
        this.add(this.chatInput, "growx");
    }

    public void scrollDown() {
        JScrollBar scrollBar = this.scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    public void removeAllMessages() {
        this.messagesPanel.removeAll();
    }

    public void addMessage(MessageComponent component, boolean myself) {
        if (myself) {
            component.setBackground(UIManager.getColor("myselfMessageColor"));

            this.messagesPanel.add(component, "wmin 200px, wmax 85%, al right");
        } else {
            this.messagesPanel.add(component, "wmin 200px, wmax 85%");
        }
    }

    public ChatInput getChatInput() {
        return this.chatInput;
    }
}
