package com.example.phdownloader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhangxinkun
 */
@Component
public final class TimerDownloader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PhConfig config;

    @Scheduled(cron = "0 0 12 * * ? *")
    public void run() {
        try {
            download();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }


    public String download() throws IOException {
        final File file = new File("./batch-file.txt");
        try {
            final List<String> list = PhApi.INSTANCE.playList(config.getPlayList());
            FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), list, "\n", false);
            final File python = new File("./PornHub-downloader-python/phdler.py");
            final Process process = Runtime.getRuntime()
                    .exec(new String[]{"python3", python.getAbsolutePath(), "custom", "batch", file.getAbsolutePath()});
            String line = null;
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (Exception ignored) {
        }
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
