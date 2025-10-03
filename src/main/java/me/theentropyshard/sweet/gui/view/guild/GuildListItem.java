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

package me.theentropyshard.sweet.gui.view.guild;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class GuildListItem extends JPanel {
    public static final Dimension SIZE = new Dimension(56, 56);

    private int rippleX, rippleY;
    private int rippleRadius;
    private float rippleAlpha;
    private Timer rippleTimer;

    public GuildListItem(Icon icon, Runnable onClick) {
        this(new JLabel(icon), onClick);
    }

    public GuildListItem(String name, Runnable onClick) {
        this(new JLabel(name.substring(0, 1)), onClick);
    }

    private GuildListItem(JLabel label, Runnable onClick) {
        this.setBackground(UIManager.getColor("secondaryContainer"));
        this.setOpaque(false);

        this.setLayout(new BorderLayout());

        label.setForeground(UIManager.getColor("onSecondaryContainer"));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        this.add(label, BorderLayout.CENTER);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                GuildListItem.this.rippleX = e.getX();
                GuildListItem.this.rippleY = e.getY();
                GuildListItem.this.rippleRadius = 0;
                GuildListItem.this.rippleAlpha = 1.0f;
                GuildListItem.this.startRippleAnimation();
            }
        });

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void startRippleAnimation() {
        if (this.rippleTimer != null && this.rippleTimer.isRunning()) {
            this.rippleTimer.stop();
        }

        this.rippleTimer = new Timer(15, e -> {
            this.rippleRadius += 5;
            this.rippleAlpha -= 0.05f;

            if (this.rippleAlpha <= 0) {
                this.rippleTimer.stop();
                this.rippleRadius = 0;
                this.rippleAlpha = 0;
            }

            this.repaint();
        });

        this.rippleTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Area backgroundArea = new Area(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
        backgroundArea.intersect(new Area(new Ellipse2D.Double(0, 0, this.getWidth(), this.getHeight())));

        g2d.setColor(this.getBackground());
        g2d.fill(backgroundArea);

        if (this.rippleAlpha > 0) {
            Composite composite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, this.rippleAlpha));
            g2d.setColor(new Color(0, 0, 0, 200));

            Area rippleArea = new Area(new Ellipse2D.Double(this.rippleX - this.rippleRadius, this.rippleY - this.rippleRadius, this.rippleRadius * 2, this.rippleRadius * 2));
            rippleArea.intersect(new Area(new Ellipse2D.Double(0, 0, this.getWidth(), this.getHeight())));

            g2d.fill(rippleArea);
            g2d.setComposite(composite);
        }

        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return GuildListItem.SIZE;
    }
}
