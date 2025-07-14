package com.reservly.apigw.config;

import com.reservly.apigw.routes.RestaurantsRoute;
import com.reservly.apigw.routes.UsersRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final UsersRoute usersRoute;
    private final RestaurantsRoute restaurantsRoute;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();

        usersRoute.addDefaultFilterRoute(routesBuilder);
        restaurantsRoute.addDefaultFilterRoute(routesBuilder);

        return routesBuilder.build();
    }
}
