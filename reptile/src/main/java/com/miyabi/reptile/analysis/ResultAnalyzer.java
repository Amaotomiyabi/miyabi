package com.miyabi.reptile.analysis;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 结果解析
 *
 * @author miyabi
 * @date 2021-03-31-14-09
 * @since 1.0
 **/


public interface ResultAnalyzer {

    String getArtist(String result) throws JsonProcessingException;

    String getCharacter(String result);

    String getGeneral(String result);

    String getSource(String result);

    String width(String result);

    String height(String result);

    String getBSource(String result);

    String getDownloadUrl(String result) throws JsonProcessingException;
}
