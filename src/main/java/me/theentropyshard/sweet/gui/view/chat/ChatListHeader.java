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

public class ChatListHeader extends JPanel {
    private final JLabel nameLabel;

    public ChatListHeader() {
        this.setLayout(new MigLayout("fillx", "[left]", "[center]"));
        this.setOpaque(false);
        this.setForeground(UIManager.getColor("onSecondaryContainer"));
        this.setBackground(UIManager.getColor("secondaryContainer"));

        this.nameLabel = new JLabel();
        this.add(this.nameLabel, "push");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(this.getBackground());
        g2d.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 16, 16);

        super.paintComponent(g);
    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }
}
