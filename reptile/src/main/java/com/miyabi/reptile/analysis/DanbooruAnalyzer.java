package com.miyabi.reptile.analysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miyabi.reptile.util.JSONUtil;

/**
 * danbooru
 *
 * @author miyabi
 * @date 2021-03-31-14-17
 * @since 1.0
 **/


public class DanbooruAnalyzer implements ResultAnalyzer {

    @Override
    public String getArtist(String result) {
        return null;
    }

    @Override
    public String getCharacter(String result) {
        return null;
    }

    @Override
    public String getGeneral(String result) {
        return null;
    }

    @Override
    public String getSource(String result) {
        return null;
    }

    @Override
    public String width(String result) {
        return null;
    }

    @Override
    public String height(String result) {
        return null;
    }

    @Override
    public String getBSource(String result) {
        return null;
    }

    @Override
    public String getDownloadUrl(String result) throws JsonProcessingException {
        return JSONUtil.strToMap(result).get("large_file_url");
    }
}
