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

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Function;

public class ChatInput extends JPanel {
    private final RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double();

    private Listener listener;

    public ChatInput() {
        MigLayout layout = new MigLayout("", "[fill][]", "[bottom, grow]");
        this.setLayout(layout);

        this.setForeground(UIManager.getColor("onSecondaryContainer"));
        this.setBackground(UIManager.getColor("secondaryContainer"));

        JTextArea area = new JTextArea();

        Action deletePrevChar = area.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        area.getActionMap().put(DefaultEditorKit.deletePrevCharAction, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (area.getDocument().getLength() != 0) {
                    deletePrevChar.actionPerformed(e);
                }
            }
        });

        area.setLineWrap(true);
        area.setOpaque(false);
        area.setForeground(UIManager.getColor("onSurface"));
        area.setBackground(UIManager.getColor("surface"));

        JScrollPane scrollPane = new JScrollPane(
            area,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        ) {
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
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        area.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setMinimumSize(new Dimension(
                    0,
                    area.getPreferredSize().height
                ));
                ChatInput.this.revalidate();
            }
        });

        area.addCaretListener(e -> {
            scrollPane.setMinimumSize(new Dimension(
                0,
                area.getPreferredSize().height
            ));
            ChatInput.this.revalidate();
        });

        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiersEx() == 0) {
                    e.consume();

                    ChatInput.this.listener.onSendMessage(area.getText());
                    area.setText("");
                }
            }
        });

        area.getActionMap().put("NEW_LINE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.append("\n");

                scrollPane.setMinimumSize(new Dimension(
                    0,
                    area.getPreferredSize().height
                ));
                ChatInput.this.revalidate();
            }
        });

        area.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), "NEW_LINE"
        );

        this.add(scrollPane, "hmax 100px, pushx, grow");

        JButton sendMessageButton = new JButton(
            new FlatSVGIcon(
                ChatInput.class.getResource("/icons/send.svg")
            ).derive(18, 18).setColorFilter(new FlatSVGIcon.ColorFilter(
                color -> UIManager.getColor("primary")
            ))
        );
        sendMessageButton.setToolTipText("Press to send message");
        sendMessageButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        sendMessageButton.addActionListener(e -> {
            ChatInput.this.listener.onSendMessage(area.getText());
            area.setText("");
        });
        this.add(sendMessageButton);

        this.setOpaque(false);
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

    public interface Listener {
        void onSendMessage(String message);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
