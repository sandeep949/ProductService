package com.project.ProductService.Config;

import com.project.ProductService.Interceptors.RequestContext;
import com.project.ProductService.Interceptors.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;


public class Clientintercept implements ClientHttpRequestInterceptor {

    private static final Logger LOG = Logger.getLogger(Clientintercept.class.getName());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Retrieve the token from ThreadLocal
        String token = RequestInterceptor.getToken();

        if (token != null) {
            // Add the token to the Authorization header
            request.getHeaders().add("Authorization", "Bearer " + token);
            LOG.info("Added token to outgoing request: " + token);
        } else {
            LOG.warning("No token found in ThreadLocal for outgoing request.");
        }

        return execution.execute(request, body);
    }
}
