package org.coffee.studio.spring.boot.http;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Slf4j
public class HttpUtils {

    private static RestTemplate restTemplate;

    private final Gson gson = new Gson();

    @PostConstruct
    private void init() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(3000);

        restTemplate = new RestTemplate(httpRequestFactory);
    }

    /**
     * get 请求
     *
     * @param url        url
     * @param parameters 参数
     * @return
     */
    public String get(String url, Map<String, String> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            return "";
        }

        try {
            ResponseEntity<String> result = restTemplate.getForEntity(url, String.class, parameters);
            if (result.getStatusCode() != HttpStatus.OK) {
                log.error("get url: {}, parameters: {}, statusCode: {}", url, gson.toJson(parameters),
                        result.getStatusCode());
                return "";
            }

            return result.getBody();
        } catch (RestClientException ex) {
            log.error("get url: {}, parameters: {}", url, gson.toJson(parameters), ex);
            return "";
        }
    }

    /**
     * post 请求
     *
     * @param url  url
     * @param body body
     * @return
     */
    public String postBody(String url, String body) {
        if (StringUtils.isEmpty(body)) {
            return "";
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body);
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(url, httpEntity, String.class);
            if (result.getStatusCode() != HttpStatus.OK) {
                log.error("postBody url: {}, parameters: {}, body: {}, statusCode: {}", url, body,
                        result.getStatusCode(), result.getStatusCode());
                return "";
            }

            return result.getBody();
        } catch (RestClientException ex) {
            log.error("postBody url: {}, parameters: {}", url, body, ex);
            return "";
        }
    }
}
