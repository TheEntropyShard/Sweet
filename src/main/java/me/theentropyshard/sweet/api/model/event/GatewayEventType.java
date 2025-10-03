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

package me.theentropyshard.sweet.api.model.event;

import java.util.HashMap;
import java.util.Map;

public enum GatewayEventType {
    MESSAGE_CREATE,
	MESSAGE_UPDATE,
	MESSAGE_DELETE,

	CHANNEL_CREATE,
	CHANNEL_UPDATE,
	CHANNEL_DELETE,

	GUILD_CREATE,
	GUILD_UPDATE,
	GUILD_DELETE,

	ROLE_CREATE,
	ROLE_MEMBER_ADD,
	ROLE_MEMBER_LEAVE,

	MEMBERS_CHUNK,

	MEMBER_JOIN,
	MEMBER_LEAVE,

	RELATIONSHIP_CREATE,
	RELATIONSHIP_UPDATE,
	RELATIONSHIP_DELETE,

	INVITE_CREATE,

	MEDIA_TOKEN_RECEIVED,

	READY,

	HEARTBEAT_ACK,

	SWEET_UNKNOWN;

	private static final Map<String, GatewayEventType> lookup = new HashMap<>();

	static {
		for (GatewayEventType type : GatewayEventType.values()) {
			GatewayEventType.lookup.put(type.name(), type);
		}
	}

	public static GatewayEventType find(String name) {
		if (name == null) {
			return GatewayEventType.SWEET_UNKNOWN;
		}

		GatewayEventType type = GatewayEventType.lookup.get(name);

		if (type == null) {
			return GatewayEventType.SWEET_UNKNOWN;
		}

		return type;
	}
}
