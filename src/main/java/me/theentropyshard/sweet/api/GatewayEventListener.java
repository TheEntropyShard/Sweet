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

import me.theentropyshard.sweet.api.model.event.server.*;

public interface GatewayEventListener {
    void onMessageCreate(ServerMessageCreateEvent event);

    void onMessageUpdate(ServerMessageUpdateEvent event);

    void onMessageDelete(ServerMessageDeleteEvent event);

    void onChannelCreate(ServerChannelCreateEvent event);

    void onChannelUpdate(ServerChannelUpdateEvent event);

    void onChannelDelete(ServerChannelDeleteEvent event);

    void onGuildCreate(ServerGuildCreateEvent event);

    void onGuildUpdate(ServerGuildUpdateEvent event);

    void onGuildDelete(ServerGuildDeleteEvent event);

    void onRoleCreate(ServerRoleCreateEvent event);

    void onRoleMemberAdd(ServerRoleMemberAddEvent event);

    void onRoleMemberLeave(ServerRoleMemberLeaveEvent event);

    void onMembersChunk(ServerMembersChunkEvent event);

    void onMemberJoin(ServerMemberJoinEvent event);

    void onMemberLeave(ServerMemberLeaveEvent event);

    void onRelationshipCreate(ServerRelationshipCreateEvent event);

    void onRelationshipUpdate(ServerRelationshipUpdateEvent event);

    void onRelationshipDelete(ServerRelationshipDeleteEvent event);

    void onInviteCreate(ServerInviteCreateEvent event);

    void onMediaTokenReceived(ServerMediaTokenReceivedEvent event);

    void onReady(ServerReadyEvent event);

    void onHeartBeat(ServerHeartBeatEvent event);
}
