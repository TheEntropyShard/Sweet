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

package me.theentropyshard.sweet.api.model.event.server;

import com.google.gson.annotations.SerializedName;
import me.theentropyshard.sweet.api.model.Member;

public class ServerRoleMemberAddEvent {
    @SerializedName("role_id")
    private String roleId;
    private String guildMention;
    private Member member;

    public ServerRoleMemberAddEvent() {

    }

    public String getRoleId() {
        return this.roleId;
    }

    public String getGuildMention() {
        return this.guildMention;
    }

    public Member getMember() {
        return this.member;
    }
}
