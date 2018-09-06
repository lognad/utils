package com.lognad.services.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.jersey.client.rx.RxWebTarget;
import org.glassfish.jersey.client.rx.java8.RxCompletionStage;
import org.glassfish.jersey.client.rx.java8.RxCompletionStageInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpService implements IHttpService {
    private static final Logger log = LoggerFactory.getLogger(HttpService.class);

    private String URL;
    private ExecutorService httpExecutor;
    private RxWebTarget<RxCompletionStageInvoker> target;

    private HttpService(String url) {
        this.URL = url;
        httpExecutor = Executors.newFixedThreadPool(5, r -> new Thread(r, "HTTP_SERVICE_THREAD"));
    }

    public HttpService(String url, String threadName) {
        this.URL = url;
        httpExecutor = Executors.newFixedThreadPool(5, r -> new Thread(r, threadName));
    }

    public HttpService(String url, String threadName, int nThreads) {
        this.URL = url;
        httpExecutor = Executors.newFixedThreadPool(nThreads, r -> new Thread(r, threadName));
    }


    private RxCompletionStageInvoker httpClient(String path, MultivaluedHashMap<String, Object> headers) {
        if (target == null)
            target = RxCompletionStage.newClient(httpExecutor)
                    .target(this.URL);

        return target.path(path).request().headers(headers).rx();
    }

    /**
     * Http Get resquest to get initial Data from the client
     *
     * @return
     */
    @Override
    public CompletableFuture<Response> get(String path, MultivaluedHashMap<String, Object> headers) {
        return httpClient(path, headers).get().toCompletableFuture();
    }

    @Override
    public CompletableFuture<Response> post(String path, Object body, MultivaluedHashMap<String, Object> headers, MediaType type) {
        return httpClient(path, headers).put(Entity.entity(body, type)).toCompletableFuture();
    }

    @Override
    public CompletableFuture<Response> put(String path, Object body, MultivaluedHashMap<String, Object> headers, MediaType type) {
        return httpClient(path, headers).put(Entity.entity(body, type)).toCompletableFuture();
    }

    @Override
    public CompletableFuture<Response> delete() {
        return null;
    }

    public CompletableFuture<Response> postWithToken(String path, Object body, String token) {
        log.info("called postWithToken, Request path: " + path + "  body: " + body + "  token: " + token);

        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle(HttpHeaders.AUTHORIZATION, token);
        headers.putSingle("Connection", "Close");

        return httpClient(path, headers)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE))
                .toCompletableFuture();

    }


}
