package com.tosh.reactivespring.router;

import com.tosh.reactivespring.handler.ItemHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.tosh.reactivespring.controller.constants.ItemConstants.ITEMS_FUNCTIONAL_ENDPOINT_V1;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ItemsRouter {

    @Bean
    public RouterFunction<ServerResponse> itemsRoute(@NotNull ItemHandler itemHandler) {
        return RouterFunctions
                .route(GET(ITEMS_FUNCTIONAL_ENDPOINT_V1).and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::getAllItems)
                .andRoute(GET(ITEMS_FUNCTIONAL_ENDPOINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::getItemById)
                .andRoute(POST(ITEMS_FUNCTIONAL_ENDPOINT_V1).and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::createItem)
                .andRoute(DELETE(ITEMS_FUNCTIONAL_ENDPOINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::deleteItemById)
                .andRoute(PUT(ITEMS_FUNCTIONAL_ENDPOINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::updateItem);

    }
}
