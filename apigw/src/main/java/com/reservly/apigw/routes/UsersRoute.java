package com.reservly.apigw.routes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersRoute {
    @Value("${services.users-url}")
    private String baseUrl;

    public void addDefaultFilterRoute(RouteLocatorBuilder.Builder builder){
        builder.route("users-service", r -> r
                .path("/users/**")
                .filters( f -> f
                        .rewritePath("/users/(?<remaining>.*)", "/${remaining}"))
                .uri(baseUrl));
    }
}
