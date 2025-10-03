package me.theentropyshard.sweet.gui.view;

import me.theentropyshard.sweet.gui.Gui;
import me.theentropyshard.sweet.gui.view.chat.ChatList;
import me.theentropyshard.sweet.gui.view.chat.ChatView;
import me.theentropyshard.sweet.gui.view.guild.GuildList;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    private final Gui gui;

    private final GuildList guildList;
    private final JPanel centerPanel;
    private final ChatList chatList;
    private final ChatView chatView;

    private Runnable onPrivateClick;

    public MainView(Gui gui, Runnable onAddClick) {
        this.gui = gui;

        this.setLayout(new BorderLayout());

        this.guildList = new GuildList(() -> this.onPrivateClick.run(), onAddClick);
        this.add(this.guildList, BorderLayout.WEST);

        this.centerPanel = new JPanel(new BorderLayout());

        this.chatList = new ChatList();
        this.centerPanel.add(this.chatList, BorderLayout.WEST);

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

    public ChatList getChatList() {
        return this.chatList;
    }

    public ChatView getChatView() {
        return this.chatView;
    }
}
