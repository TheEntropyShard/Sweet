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

package me.theentropyshard.sweet.api.model;

import com.google.gson.annotations.SerializedName;

public class Role {
    private String id;
    private String name;

    @SerializedName("guild")
    private String guildMention;

    private int[] allow;
    private int[] deny;

    public Role() {

    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGuildMention() {
        return this.guildMention;
    }

    public Permission[] getAllow() {
        return Role.intArrayToPermissions(this.allow);
    }

    public Permission[] getDeny() {
        return Role.intArrayToPermissions(this.deny);
    }

    private static Permission[] intArrayToPermissions(int[] array) {
        Permission[] permissions = new Permission[array.length];

        for (int i = 0; i < array.length; i++) {
            permissions[i] = Permission.fromId(array[i]);
        }

        return permissions;
    }

    public enum Permission {
        NONE,

        /**
         * all permissions + delete
         */
        OWNER,

        /**
         * all permissions
         */
        ADMIN,

        /**
         * can send messages in this channel
         */
        SEND_MESSAGES,

        /**
         * can modify, delete, add channels in this guild
         */
        MANAGE_CHANNELS,

        /**
         * can view this channel.
         */
        VIEW_CHANNEL,

        /**
         * can start or join this channel's voice call
         */
        CALL_CHANNEL,

        /**
         * can modify this guild
         */
        MANAGE_GUILD,

        /**
         * can modify or delete invites
         */
        MANAGE_INVITES,

        /**
         * can invite people to this channel
         */
        CREATE_INVITE,

        /**
         * can attach files to this channel
         */
        UPLOAD,

        /**
         * can delete any message in this channel
         */
        MANAGE_MESSAGES;

        public static Permission fromId(int id) {
            return switch (id) {
                case 1 -> Permission.OWNER;
                case 2 -> Permission.ADMIN;
                case 3 -> Permission.SEND_MESSAGES;
                case 4 -> Permission.MANAGE_CHANNELS;
                case 5 -> Permission.VIEW_CHANNEL;
                case 6 -> Permission.CALL_CHANNEL;
                case 7 -> Permission.MANAGE_GUILD;
                case 8 -> Permission.MANAGE_INVITES;
                case 9 -> Permission.CREATE_INVITE;
                case 10 -> Permission.UPLOAD;
                case 11 -> Permission.MANAGE_MESSAGES;
                default -> Permission.NONE;
            };
        }
    }
}
