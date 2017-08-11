package com.axiang.smallyellowduck.data.guokr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/29.
 */

public class GuokrContentResult {

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("is_replyable")
    private boolean isReplyable;

    @Expose
    @SerializedName("channels")
    private List<GuokrContentChannel> channels;

    @Expose
    @SerializedName("channel_keys")
    private List<String> channelKeys;

    @Expose
    @SerializedName("preface")
    private String preface;

    @Expose
    @SerializedName("subject")
    private GuokrContentChannel subject;

    @Expose
    @SerializedName("copyright")
    private String copyright;

    @Expose
    @SerializedName("author")
    private GuokrNewsAuthor author;

    @Expose
    @SerializedName("image_description")
    private String imageDescription;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("is_show_summary")
    private boolean isShowSummary;

    @Expose
    @SerializedName("minisite_key")
    private String minisiteKey;

    @Expose
    @SerializedName("image_info")
    private GuokrContentImageInfo imageInfo;

    @Expose
    @SerializedName("subject_key")
    private String subjectKey;

    @Expose
    @SerializedName("minisite")
    private GuokrContentMinisite minisite;

    @Expose
    @SerializedName("tags")
    private List<String> tags;

    @Expose
    @SerializedName("date_published")
    private String datePublished;

    @Expose
    @SerializedName("replies_count")
    private int repliesCount;

    @Expose
    @SerializedName("is_author_external")
    private boolean isAuthorExternal;

    @Expose
    @SerializedName("recommends_count")
    private int recommendsCount;

    @Expose
    @SerializedName("title_hide")
    private String titleHide;

    @Expose
    @SerializedName("date_modified")
    private String dateModified;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("small_image")
    private String smallImage;

    @Expose
    @SerializedName("summary")
    private String summary;

    @Expose
    @SerializedName("ukey_author")
    private String ukeyAuthor;

    @Expose
    @SerializedName("date_created")
    private String dateCreated;

    @Expose
    @SerializedName("resource_url")
    private String resourceUrl;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isReplyable() {
        return isReplyable;
    }

    public void setReplyable(boolean replyable) {
        isReplyable = replyable;
    }

    public List<String> getChannelKeys() {
        return channelKeys;
    }

    public void setChannelKeys(List<String> channelKeys) {
        this.channelKeys = channelKeys;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowSummary() {
        return isShowSummary;
    }

    public void setShowSummary(boolean showSummary) {
        isShowSummary = showSummary;
    }

    public String getMinisiteKey() {
        return minisiteKey;
    }

    public void setMinisiteKey(String minisiteKey) {
        this.minisiteKey = minisiteKey;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    public boolean isAuthorExternal() {
        return isAuthorExternal;
    }

    public void setAuthorExternal(boolean authorExternal) {
        isAuthorExternal = authorExternal;
    }

    public int getRecommendsCount() {
        return recommendsCount;
    }

    public void setRecommendsCount(int recommendsCount) {
        this.recommendsCount = recommendsCount;
    }

    public String getTitleHide() {
        return titleHide;
    }

    public void setTitleHide(String titleHide) {
        this.titleHide = titleHide;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUkeyAuthor() {
        return ukeyAuthor;
    }

    public void setUkeyAuthor(String ukeyAuthor) {
        this.ukeyAuthor = ukeyAuthor;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public List<GuokrContentChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<GuokrContentChannel> channels) {
        this.channels = channels;
    }

    public GuokrContentChannel getSubject() {
        return subject;
    }

    public void setSubject(GuokrContentChannel subject) {
        this.subject = subject;
    }

    public GuokrNewsAuthor getAuthor() {
        return author;
    }

    public void setAuthor(GuokrNewsAuthor author) {
        this.author = author;
    }

    public GuokrContentImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(GuokrContentImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public GuokrContentMinisite getMinisite() {
        return minisite;
    }

    public void setMinisite(GuokrContentMinisite minisite) {
        this.minisite = minisite;
    }
}
