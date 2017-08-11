package com.axiang.smallyellowduck.data.guokr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a2389 on 2017/7/29.
 */

public class GuokrNewsAuthor {

    @Expose
    @SerializedName("ukey")
    private String ukey;

    @Expose
    @SerializedName("is_title_authorized")
    private String isTitleAuthorized;

    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("master_category")
    private String masterCategory;

    @Expose
    @SerializedName("amended_reliability")
    private String amendedReliability;

    @Expose
    @SerializedName("is_exists")
    private boolean isExists;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("followers_count")
    private int followersCount;

    @Expose
    @SerializedName("avatar")
    private GuokrNewsAvatar avatar;

    @Expose
    @SerializedName("resource_url")
    private String resourceUrl;

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public String getIsTitleAuthorized() {
        return isTitleAuthorized;
    }

    public void setIsTitleAuthorized(String isTitleAuthorized) {
        this.isTitleAuthorized = isTitleAuthorized;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMasterCategory() {
        return masterCategory;
    }

    public void setMasterCategory(String masterCategory) {
        this.masterCategory = masterCategory;
    }

    public String getAmendedReliability() {
        return amendedReliability;
    }

    public void setAmendedReliability(String amendedReliability) {
        this.amendedReliability = amendedReliability;
    }

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public GuokrNewsAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(GuokrNewsAvatar avatar) {
        this.avatar = avatar;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
