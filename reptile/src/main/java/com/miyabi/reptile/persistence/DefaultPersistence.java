package com.miyabi.reptile.persistence;

import com.miyabi.reptile.analysis.ResultAnalyzer;
import com.miyabi.reptile.net.download.Downloader;

import java.io.IOException;
import java.nio.file.Path;

/**
 * danbooru
 *
 * @author miyabi
 * @date 2021-03-31-15-00
 * @since 1.0
 **/


public class DefaultPersistence implements Persistence {
    private final ResultAnalyzer analyzer;
    private Downloader downloader;

    public DefaultPersistence(ResultAnalyzer analyzer, Downloader downloader) {
        this.analyzer = analyzer;
        this.downloader = downloader;
    }

    @Override
    public String saveBaseInfo(String result) throws IOException, InterruptedException {
        analyzer.getArtist(result);
        analyzer.getCharacter(result);
        analyzer.getGeneral(result);
        analyzer.getBSource(result);
        analyzer.getSource(result);
        if (analyzer.getDownloadUrl(result) != null) {
            return downloadImg(analyzer.getDownloadUrl(result)).toString();
        }
        return null;
    }

    @Override
    public Path downloadImg(String url) throws IOException, InterruptedException {
        return downloader.download(url);
    }
}
