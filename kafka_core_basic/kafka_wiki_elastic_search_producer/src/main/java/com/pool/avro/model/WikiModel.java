package com.pool.avro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("title_url")
    private String titleUrl;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("user")
    private String user;
    @JsonProperty("bot")
    private String bot;
    @JsonProperty("minor")
    private String minor;
    @JsonProperty("notify_url")
    private String notifyUrl;

    public WikiModel() {

    }

    public WikiModel(Long id, String type, String titleUrl, String comment, String user, String bot, String minor, String notifyUrl) {
        this.id = id;
        this.type = type;
        this.titleUrl = titleUrl;
        this.comment = comment;
        this.user = user;
        this.bot = bot;
        this.minor = minor;
        this.notifyUrl = notifyUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String toString() {
        return "WikiModel{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", comment='" + comment + '\'' +
                ", user='" + user + '\'' +
                ", bot='" + bot + '\'' +
                ", minor='" + minor + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
