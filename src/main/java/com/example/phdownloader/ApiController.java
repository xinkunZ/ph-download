package com.example.phdownloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author zhangxinkun
 */
@RestController
public final class ApiController {
    @Autowired
    private TimerDownloader downloader;

    @GetMapping("/now")
    public String downloadNow() throws IOException {
        return downloader.download();
    }
}
