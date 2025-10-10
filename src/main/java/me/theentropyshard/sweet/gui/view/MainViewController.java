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

import me.theentropyshard.sweet.Sweet;
import me.theentropyshard.sweet.api.GatewayEventListener;
import me.theentropyshard.sweet.api.model.Channel;
import me.theentropyshard.sweet.api.model.Guild;
import me.theentropyshard.sweet.api.model.Message;
import me.theentropyshard.sweet.api.model.Relationship;
import me.theentropyshard.sweet.api.model.event.server.*;
import me.theentropyshard.sweet.gui.view.chat.ChatList;
import me.theentropyshard.sweet.gui.view.chat.ChatListItem;
import me.theentropyshard.sweet.gui.view.chat.ChatView;
import me.theentropyshard.sweet.gui.view.chat.MessageComponent;
import me.theentropyshard.sweet.gui.view.guild.GuildList;
import me.theentropyshard.sweet.gui.view.guild.GuildListItem;
import me.theentropyshard.sweet.utils.CollectionUtils;
import me.theentropyshard.sweet.utils.SwingUtils;

import javax.swing.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainViewController implements GatewayEventListener {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final MainView mainView;

    private final List<Guild> guilds;
    private final List<Channel> privateChannels;
    private final List<Relationship> relationships;

    private Channel currentChannel;

    public MainViewController(MainView mainView) {
        this.mainView = mainView;

        this.guilds = new ArrayList<>();
        this.privateChannels = new ArrayList<>();
        this.relationships = new ArrayList<>();

        Sweet.getInstance().getShootClient().addGatewayListener(this);

        mainView.getChatView().getChatInput().setListener(message -> {
            SwingUtils.runWorker(() -> {
                try {
                    Sweet.getInstance().getShootClient().sendMessage(this.currentChannel.getMention(), message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        mainView.setOnPrivateClick(this::showPrivate);
    }

    private void updateChatList(List<Channel> channels) {
        SwingUtilities.invokeLater(() -> {
            ChatList chatList = this.mainView.getChatList();
            chatList.removeAll();
            for (Channel channel : channels) {
                ChatListItem item = new ChatListItem(() -> this.showChannel(channel));
                item.setName(channel.getName());
                chatList.addChat(item);
            }
            chatList.repaint();
            chatList.revalidate();
        });
    }

    public void showPrivate() {
        SwingUtilities.invokeLater(() -> this.mainView.getChatListHeader().setName("Private Channels"));

        this.updateChatList(this.privateChannels);
    }

    public void showGuild(String mention) {
        Guild guild = CollectionUtils.find(this.guilds, g -> g.getMention().equals(mention));

        if (guild == null) {
            return;
        }

        SwingUtilities.invokeLater(() -> this.mainView.getChatListHeader().setName(guild.getName()));

        this.updateChatList(guild.getChannels());
    }

    public void showChannel(Channel channel) {
        this.currentChannel = channel;

        SwingUtils.runWorker(() -> {
            try {
                Message[] messages = Sweet.getInstance().getShootClient().getMessages(channel.getMention());

                SwingUtilities.invokeLater(() -> {
                    ChatView chatView = this.mainView.getChatView();

                    chatView.removeAllMessages();

                    for (Message message : messages) {
                        chatView.addMessage(new MessageComponent(
                            message.getAuthorId(),
                            OffsetDateTime.parse(message.getPublished())
                                .atZoneSameInstant(ZoneId.systemDefault()).format(MainViewController.FORMATTER),
                            message.getContent()
                        ));
                    }

                    chatView.revalidate();

                    SwingUtilities.invokeLater(chatView::scrollDown);
                });

                Sweet.getInstance().getShootClient().subscribeForMembers(channel.getMention());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onMessageCreate(ServerMessageCreateEvent event) {
        SwingUtilities.invokeLater(() -> {
            ChatView chatView = this.mainView.getChatView();

            chatView.addMessage(new MessageComponent(
                event.getMessage().getAuthorId(),
                OffsetDateTime.parse(event.getMessage().getPublished())
                    .atZoneSameInstant(ZoneId.systemDefault()).format(MainViewController.FORMATTER),
                event.getMessage().getContent()
            ));

            chatView.revalidate();
            SwingUtilities.invokeLater(chatView::scrollDown);
        });
    }

    @Override
    public void onMessageUpdate(ServerMessageUpdateEvent event) {

    }

    @Override
    public void onMessageDelete(ServerMessageDeleteEvent event) {

    }

    @Override
    public void onChannelCreate(ServerChannelCreateEvent event) {

    }

    @Override
    public void onChannelUpdate(ServerChannelUpdateEvent event) {

    }

    @Override
    public void onChannelDelete(ServerChannelDeleteEvent event) {

    }

    @Override
    public void onGuildCreate(ServerGuildCreateEvent event) {

    }

    @Override
    public void onGuildUpdate(ServerGuildUpdateEvent event) {

    }

    @Override
    public void onGuildDelete(ServerGuildDeleteEvent event) {

    }

    @Override
    public void onRoleCreate(ServerRoleCreateEvent event) {

    }

    @Override
    public void onRoleMemberAdd(ServerRoleMemberAddEvent event) {

    }

    @Override
    public void onRoleMemberLeave(ServerRoleMemberLeaveEvent event) {

    }

    @Override
    public void onMembersChunk(ServerMembersChunkEvent event) {

    }

    @Override
    public void onMemberJoin(ServerMemberJoinEvent event) {

    }

    @Override
    public void onMemberLeave(ServerMemberLeaveEvent event) {

    }

    @Override
    public void onRelationshipCreate(ServerRelationshipCreateEvent event) {

    }

    @Override
    public void onRelationshipUpdate(ServerRelationshipUpdateEvent event) {

    }

    @Override
    public void onRelationshipDelete(ServerRelationshipDeleteEvent event) {

    }

    @Override
    public void onInviteCreate(ServerInviteCreateEvent event) {

    }

    @Override
    public void onMediaTokenReceived(ServerMediaTokenReceivedEvent event) {

    }

    @Override
    public void onReady(ServerReadyEvent event) {
        this.guilds.addAll(event.getGuilds());
        this.privateChannels.addAll(event.getChannels());
        this.relationships.addAll(event.getRelationships());

        SwingUtilities.invokeLater(() -> {
            GuildList guildList = this.mainView.getGuildList();

            for (Guild guild : this.guilds) {
                guildList.addItem(new GuildListItem(guild.getName(), () -> this.showGuild(guild.getMention())));
            }

            this.mainView.showMain();
        });
    }

    @Override
    public void onHeartBeat(ServerHeartBeatEvent event) {

    }
}
