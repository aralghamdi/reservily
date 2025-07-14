package com.reservly.apigw.routes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantsRoute {
    @Value("${services.restaurants-url}")
    private String baseUrl;

    public void addDefaultFilterRoute(RouteLocatorBuilder.Builder builder){
        builder.route("restaurants-service", r -> r
                .path("/restaurants/**")
                .filters( f -> f
                        .rewritePath("/restaurants/(?<remaining>.*)", "/${remaining}"))
                .uri(baseUrl));
    }
}
