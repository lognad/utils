package com.lognad.services.http;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

public interface IHttpService {
    CompletableFuture<Response> get(String path, MultivaluedHashMap<String, Object> headers);

    CompletableFuture<Response> post(String path, Object body, MultivaluedHashMap<String, Object> headers, MediaType type);

    CompletableFuture<Response> put(String path, Object body, MultivaluedHashMap<String, Object> headers, MediaType type);

    CompletableFuture<Response> delete();
}
