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

import com.formdev.flatlaf.extras.FlatSVGIcon;
import me.theentropyshard.sweet.Sweet;
import me.theentropyshard.sweet.api.GatewayEventAdapter;
import me.theentropyshard.sweet.api.model.Guild;
import me.theentropyshard.sweet.api.model.event.server.ServerReadyEvent;
import me.theentropyshard.sweet.gui.FlatSmoothScrollPaneUI;
import me.theentropyshard.sweet.gui.Gui;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class GuildList extends JPanel {
    private final JPanel guildsPanel;

    public GuildList(Runnable onPrivateClick, Runnable onAddClick) {
        this.setLayout(new BorderLayout());

        this.guildsPanel = new JPanel(new MigLayout("wrap 1", "[center]", "[top]"));

        JScrollPane scrollPane = new JScrollPane(
            this.guildsPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setUI(new FlatSmoothScrollPaneUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(scrollPane, BorderLayout.CENTER);

        this.guildsPanel.add(new GuildListItem(
            new FlatSVGIcon(GuildList.class.getResource("/icons/person.svg"))
                .setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("onSecondaryContainer"))),
            onPrivateClick
        ));

        this.guildsPanel.add(new GuildListItem(
            new FlatSVGIcon(GuildList.class.getResource("/icons/add.svg"))
                .setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("onSecondaryContainer"))),
            onAddClick
        ));
    }

    public void addItem(GuildListItem item) {
        this.guildsPanel.add(item, this.guildsPanel.getComponentCount() - 1);
    }
}
