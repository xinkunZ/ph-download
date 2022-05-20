package com.example.phdownloader;

import org.apache.commons.collections4.CollectionUtils;
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
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhangxinkun
 */
@Component
public final class TimerDownloader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PhConfig config;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void run() {
        try {
            lock.acquire();
            download();
        } catch (Exception e) {
            logger.error("error", e);
        } finally {
            lock.release();
        }
    }


    public String download() throws IOException {
        final File file = new File("./batch-file.txt");
        try {
            final String regex = "[\\pP\\pS\\pZ]";
            final List<PhApi.VideoName> videoNames = PhApi.INSTANCE.playList(config.getPlayList());
            final List<String> files = FileUtils.listFiles(
                            new File(config.getDownloadPath(), "handpicked"),
                            null, false)
                    .stream().map(File::getName)
                    .map(it -> it.replaceAll(regex, ""))
                    .collect(Collectors.toList());
            videoNames.removeIf(it -> files.contains((it.getName() + ".mp4").replaceAll(regex, "")));
            FileUtils.writeLines(file, StandardCharsets.UTF_8.name(),
                    videoNames.stream().map(PhApi.VideoName::getUrl).collect(
                            Collectors.toList()), "\n", false);
            if (CollectionUtils.isEmpty(videoNames)) {
                return "all files download success!";
            }
            Executors.newFixedThreadPool(1).submit(() -> doDownload(file));
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    private final Semaphore lock = new Semaphore(1);

    private void doDownload(final File batchFile) {
        try {
            lock.acquire();
            final File python = new File("./PornHub-downloader-python/phdler.py");
            final Process process = Runtime.getRuntime()
                    .exec(new String[]{"python3", python.getAbsolutePath(), "custom", "batch", batchFile.getAbsolutePath()});
            String line = null;
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
            logger.info("download success!");
        } catch (Exception e) {
            logger.error("fail: ", e);
        } finally {
            lock.release();
        }
    }
}
