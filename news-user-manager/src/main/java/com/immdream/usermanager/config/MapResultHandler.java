package com.immdream.usermanager.config;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.usermanager.config
 *
 * @author immDream
 * @date 2023/05/30/14:23
 * @since 1.8
 */
public class MapResultHandler implements ResultHandler<Map> {
    private final Map<String, String> mappedResults = new HashMap();

    @Override
    public void handleResult(ResultContext resultContext) {
        Map<String, String> m = (Map) resultContext.getResultObject();
        mappedResults.put(m.get("key"), m.get("value"));
    }

    public Map getMappedResults() {
        return mappedResults;
    }
}
