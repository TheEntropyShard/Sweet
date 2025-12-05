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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MessageComponent extends JPanel {
    private final RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double();

    public MessageComponent(String author, String datetime, String text) {
        super(new MigLayout("fill", "[]", "[][]"));

        this.setForeground(UIManager.getColor("onSecondaryContainer"));
        this.setBackground(UIManager.getColor("secondaryContainer"));

        this.setOpaque(false);

        JPanel metaInfoPanel = new JPanel(new MigLayout("fillx, insets 0", "[]20 push[]", "[]"));
        metaInfoPanel.setOpaque(false);
        metaInfoPanel.add(new JLabel(author));
        metaInfoPanel.add(new JLabel(datetime));
        this.add(metaInfoPanel, "growx, wrap");

        JTextPane pane = new JTextPane() {
            private final RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double();

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                this.roundRect.width = this.getWidth();
                this.roundRect.height = this.getHeight();
                this.roundRect.arcwidth = this.roundRect.archeight = 8;

                g2d.setColor(this.getBackground());
                g2d.fill(this.roundRect);

                super.paintComponent(g);
            }
        };
        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 3) {
                    pane.selectAll();
                }
            }
        });
        pane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        pane.setCaretColor(UIManager.getColor("surface"));
        pane.setOpaque(false);
        pane.setForeground(UIManager.getColor("onSurface"));
        pane.setBackground(UIManager.getColor("surface"));
        pane.setEditable(false);
        pane.setText(text);
        this.add(pane, "grow");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.roundRect.width = this.getWidth();
        this.roundRect.height = this.getHeight();
        this.roundRect.arcwidth = this.roundRect.archeight = 16;

        g2d.setColor(this.getBackground());
        g2d.fill(this.roundRect);

        super.paintComponent(g2d);
    }
}
