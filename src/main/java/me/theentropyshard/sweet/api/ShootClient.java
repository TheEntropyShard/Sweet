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

package me.theentropyshard.sweet.api;

import com.google.gson.*;
import me.theentropyshard.sweet.api.model.ChatMember;
import me.theentropyshard.sweet.api.model.event.client.ClientSubscribeForMembersEvent;
import me.theentropyshard.sweet.api.model.event.server.*;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import me.theentropyshard.sweet.api.model.Message;
import me.theentropyshard.sweet.api.model.event.GatewayEvent;
import me.theentropyshard.sweet.api.model.event.client.ClientGatewayEvent;
import me.theentropyshard.sweet.api.model.event.client.ClientHeartbeatEvent;
import me.theentropyshard.sweet.api.model.event.client.ClientIdentifyEvent;
import me.theentropyshard.sweet.api.model.http.LoginRequest;
import me.theentropyshard.sweet.api.model.http.LoginResponse;

public class ShootClient extends WebSocketListener {
    private static final Logger LOG = LogManager.getLogger(ShootClient.class);

    private final OkHttpClient httpClient;
    private final Gson gson;
    private final Set<GatewayEventListener> listeners;

    private String instance;
    private String token;
    private WebSocket webSocket;
    private int clientSequence;

    private boolean closeInitiated;

    public ShootClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        this.gson = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().create();
        this.listeners = new HashSet<>();
    }

    private void send(ClientGatewayEvent event) {
        String json = ShootClient.this.gson.toJson(event);

        ShootClient.this.webSocket.send(json);
    }

    public void login(String username, String password, LoginCallback callback) {
        RequestBody body = RequestBody.create(
            this.gson.toJson(new LoginRequest(username, password)).getBytes(StandardCharsets.UTF_8),
            MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
            .url(this.instance + "/auth/login")
            .post(body)
            .build();

        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    LoginResponse r = ShootClient.this.gson.fromJson(response.body().string(), LoginResponse.class);
                    ShootClient.this.token = r.getToken();
                    ShootClient.this.createWebSocket();
                    callback.onSuccess();
                } else {
                    callback.onError();
                }
            }
        });
    }

    public Message[] getMessages(String channelMention) throws IOException {
        Request request = new Request.Builder()
            .header("Authorization", "Bearer " + this.token)
            .url(this.instance + "/channel/" + channelMention + "/messages?limit=50&order=ASC")
            .build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return this.gson.fromJson(response.body().string(), Message[].class);
        }
    }

    public void sendMessage(String channelMention, String message) throws IOException {
        message = message.replace("\n", "\\n");

        RequestBody body = RequestBody.create(("{\"content\": \"" + message + "\"}").getBytes(StandardCharsets.UTF_8),
            MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .header("Authorization", "Bearer " + this.token)
            .url(this.instance + "/channel/" + channelMention + "/messages")
            .post(body)
            .build();

        try (Response response = this.httpClient.newCall(request).execute()) {

        }
    }

    public void subscribeForMembers(String channelMention) throws IOException {
        this.send(new ClientSubscribeForMembersEvent(channelMention, 0, 100));
    }

    private void startHeartbeat() {
        new Timer(true).schedule(
            new TimerTask() {
                @Override
                public void run() {
                    ShootClient.this.send(new ClientHeartbeatEvent(ShootClient.this.clientSequence));
                }
            }, 0, 4500L
        );
    }

    private void createWebSocket() {
        String url;

        if (this.instance.startsWith("http://")) {
            url = this.instance.replace("http", "ws");
        } else if (this.instance.startsWith("https://")) {
            url = this.instance.replace("https", "wss");
        } else {
            throw new RuntimeException("Wrong instance url: $instance");
        }

        Request request = new Request.Builder()
            .url(url)
            .build();

        this.webSocket = this.httpClient.newWebSocket(request, this);
    }

    public void closeWebSocket() {
        if (this.webSocket != null) {
            this.closeInitiated = true;

            this.webSocket.close(1000, null);
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        this.send(new ClientIdentifyEvent(this.token));
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        if (code == 1000) {
            ShootClient.LOG.info("WebSocket was successfully closed");
        } else {
            ShootClient.LOG.info("WebSocket was closed with code {} because {}", code, reason);
        }

        if (this.closeInitiated) { // FIXME: This is bad
            System.exit(0);
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        GatewayEvent event = this.gson.fromJson(text, GatewayEvent.class);

        if (this.checkEvent(event)) {
            return;
        }

        this.checkSequence(event.getServerSequence());

        switch (event.getType()) {
            case MESSAGE_CREATE -> {
                ServerMessageCreateEvent messageCreateEvent = this.gson.fromJson(event.getData(), ServerMessageCreateEvent.class);

                this.listeners.forEach(listener -> listener.onMessageCreate(messageCreateEvent));
            }

            case MESSAGE_UPDATE -> {
                ServerMessageUpdateEvent messageUpdateEvent = this.gson.fromJson(event.getData(), ServerMessageUpdateEvent.class);

                this.listeners.forEach(listener -> listener.onMessageUpdate(messageUpdateEvent));
            }

            case MESSAGE_DELETE -> {
                ServerMessageDeleteEvent messageDeleteEvent = this.gson.fromJson(event.getData(), ServerMessageDeleteEvent.class);

                this.listeners.forEach(listener -> listener.onMessageDelete(messageDeleteEvent));
            }

            case CHANNEL_CREATE -> {
                ServerChannelCreateEvent channelCreateEvent = this.gson.fromJson(event.getData(), ServerChannelCreateEvent.class);

                this.listeners.forEach(listener -> listener.onChannelCreate(channelCreateEvent));
            }

            case CHANNEL_UPDATE -> {
                ServerChannelUpdateEvent channelUpdateEvent = this.gson.fromJson(event.getData(), ServerChannelUpdateEvent.class);

                this.listeners.forEach(listener -> listener.onChannelUpdate(channelUpdateEvent));
            }

            case CHANNEL_DELETE -> {
                ServerChannelDeleteEvent channelDeleteEvent = this.gson.fromJson(event.getData(), ServerChannelDeleteEvent.class);

                this.listeners.forEach(listener -> listener.onChannelDelete(channelDeleteEvent));
            }

            case GUILD_CREATE -> {
                ServerGuildCreateEvent guildCreateEvent = this.gson.fromJson(event.getData(), ServerGuildCreateEvent.class);

                this.listeners.forEach(listener -> listener.onGuildCreate(guildCreateEvent));
            }

            case GUILD_UPDATE -> {

            }

            case GUILD_DELETE -> {

            }

            case ROLE_CREATE -> {

            }

            case ROLE_MEMBER_ADD -> {

            }

            case ROLE_MEMBER_LEAVE -> {

            }

            case MEMBERS_CHUNK -> {
                List<ChatMember> members = new ArrayList<>();

                String currentRoleId = "";

                for (JsonElement item : event.getData().getAsJsonArray("items")) {
                    if (item.isJsonPrimitive()) {
                        currentRoleId = item.getAsString();
                    } else if (item.isJsonObject()) {
                        JsonObject object = item.getAsJsonObject();

                        JsonElement userId = object.get("user_id");
                        JsonElement memberId = object.get("member_id");
                        JsonElement name = object.get("name");

                        ChatMember member = new ChatMember(
                            currentRoleId,
                            userId.getAsString(),
                            memberId == null ? null : memberId.getAsString(),
                            name.getAsString()
                        );

                        members.add(member);
                    }
                }

                ServerMembersChunkEvent membersChunkEvent = new ServerMembersChunkEvent(members);

                this.listeners.forEach(listener -> listener.onMembersChunk(membersChunkEvent));
            }

            case MEMBER_JOIN -> {

            }

            case MEMBER_LEAVE -> {

            }

            case RELATIONSHIP_CREATE -> {

            }

            case RELATIONSHIP_UPDATE -> {

            }

            case RELATIONSHIP_DELETE -> {

            }

            case INVITE_CREATE -> {

            }

            case MEDIA_TOKEN_RECEIVED -> {

            }

            case READY -> {
                this.startHeartbeat();

                ServerReadyEvent readyEvent = this.gson.fromJson(event.getData(), ServerReadyEvent.class);

                this.listeners.forEach(listener -> listener.onReady(readyEvent));
            }

            case HEARTBEAT_ACK -> {

            }

            case SWEET_UNKNOWN -> {
                ShootClient.LOG.warn("Unknown WebSocket event: {}", event.getRawType());
            }
        }
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        ShootClient.LOG.error("WebSocket connection failed", t);
    }

    private boolean checkEvent(GatewayEvent event) {
        if (event.getRawType() == null) {
            ShootClient.LOG.warn("Malformed WebSocket message: type field \"t\" is not present");

            return true;
        }

        if (event.getData() == null) {
            ShootClient.LOG.warn("Malformed WebSocket message: data field \"d\" is not present");

            return true;
        }

        if (event.getServerSequence() == null) {
            ShootClient.LOG.warn("Malformed WebSocket message: sequence field \"s\" is not present");

            return true;
        }

        return false;
    }

    private void checkSequence(int serverSequence) {
        int clientSequence = this.clientSequence++;

        if (serverSequence != clientSequence) {
            throw new RuntimeException("Out of sync. Sequence mismatch: serverSequence = " + serverSequence + ", clientSequence = " + clientSequence);
        }
    }

    public void addGatewayListener(GatewayEventListener listener) {
        this.listeners.add(listener);
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
