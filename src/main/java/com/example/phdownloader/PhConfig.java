package com.example.phdownloader;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangxinkun
 *
 */
@Component
@ConfigurationProperties("ph-config")
public final class PhConfig {
    private String playList = "229677091";

    public String getPlayList() {
        return playList;
    }

    public void setPlayList(final String playList) {
        this.playList = playList;
    }
}
