package com.example.phdownloader;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangxinkun
 */
@Component
@ConfigurationProperties("ph-config")
public final class PhConfig {
    private String playList = "229677091";

    private String cookie = "./config/cookies.txt";

    private String download = "./download";

    public String getCookie() {
        return cookie;
    }

    public void setCookie(final String cookie) {
        this.cookie = cookie;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(final String download) {
        this.download = download;
    }

    public String getPlayList() {
        return playList;
    }

    public void setPlayList(final String playList) {
        this.playList = playList;
    }
}
