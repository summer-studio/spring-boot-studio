package org.coffee.studio.spring.boot.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpUtilsTest {

    @Autowired
    private HttpUtils httpUtils;

    @Test
    public void get() {
        Map<String, String> map = new HashMap<>(16);
        map.put("key", "1");
        String content = httpUtils.get("https://www.baidu.com", map);
        System.out.println(content);
    }

    @Test
    public void post() {
        String body = "{}";
        String content = httpUtils.postBody("https://www.baidu.com", body);
        System.out.println(content);
    }
}
