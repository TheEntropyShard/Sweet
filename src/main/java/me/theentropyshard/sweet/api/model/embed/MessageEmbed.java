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

package me.theentropyshard.sweet.api.model.embed;

import com.google.gson.annotations.SerializedName;

public class MessageEmbed {
    @SerializedName("target")
    private String target;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("type")
    private int type;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("images")
    private EmbedImage[] images;

    @SerializedName("videos")
    private EmbedVideo[] videos;

    @SerializedName("author")
    private EmbedAuthor author;

    @SerializedName("footer")
    private EmbedFooter footer;

    @SerializedName("provider")
    private EmbedProvider provider;

    public MessageEmbed() {

    }

    public String getTarget() {
        return this.target;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public EmbedImage[] getImages() {
        return this.images;
    }

    public EmbedVideo[] getVideos() {
        return this.videos;
    }

    public EmbedAuthor getAuthor() {
        return this.author;
    }

    public EmbedFooter getFooter() {
        return this.footer;
    }

    public EmbedProvider getProvider() {
        return this.provider;
    }
}
