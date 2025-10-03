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
import me.theentropyshard.sweet.api.model.embed.MessageEmbed;

public class Message {
    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("published")
    private String published;

    @SerializedName("updated")
    private String updated;

    @SerializedName("author_id")
    private String authorId;

    @SerializedName("channel_id")
    private String channelId;

    @SerializedName("files")
    private MessageFile[] files;

    @SerializedName("embeds")
    private MessageEmbed[] embeds;

    public Message() {

    }

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public String getPublished() {
        return this.published;
    }

    public String getUpdated() {
        return this.updated;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public MessageFile[] getFiles() {
        return this.files;
    }

    public MessageEmbed[] getEmbeds() {
        return this.embeds;
    }
}
