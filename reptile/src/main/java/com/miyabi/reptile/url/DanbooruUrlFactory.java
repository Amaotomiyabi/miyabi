package com.miyabi.reptile.url;

import com.miyabi.reptile.net.Client;
import com.miyabi.reptile.net.RequestFactory;
import com.miyabi.reptile.util.JSONUtil;

import java.io.IOException;

/**
 * Danbooru接口
 *
 * @author miyabi
 * @date 2021-03-31-13-30
 * @since 1.0
 **/


public class DanbooruUrlFactory implements UrlFactory {
    private final String baseUrl = "https://danbooru.donmai.us/posts";
    private final Client client;
    private long id;

    public DanbooruUrlFactory(long id, Client client) {
        this.id = id - 1;
        this.client = client;
    }

    @Override
    public String getNextObjUrl() {
        return baseUrl + "/" + id++ + ".json";
    }

    @Override
    public String getPageUrl(int page, int limit) {
        return baseUrl + ".json?page=" + page + "&limit=" + limit;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getMaxId() throws IOException, InterruptedException {
        var req = RequestFactory.getInstance().getRequest(getPageUrl(2, 10));
        var maxId = String.valueOf(JSONUtil.strToList(client.sendRetString(req).body()).get(0).get("id"));
        return Long.parseLong(maxId);
    }
}
