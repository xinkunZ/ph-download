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

    private String downloadPath = "/Volumes/132XXXX3776/PT/auto";

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(final String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getPlayList() {
        return playList;
    }

    public void setPlayList(final String playList) {
        this.playList = playList;
    }
}
