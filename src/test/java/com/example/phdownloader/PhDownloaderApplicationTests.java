package com.example.phdownloader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest
class PhDownloaderApplicationTests {
    @Autowired
    private PhConfig config;
    @Test
    void contextLoads() throws URISyntaxException, IOException {
        System.out.println(PhApi.INSTANCE.playList(config.getPlayList()));
    }

}
