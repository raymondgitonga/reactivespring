package com.tosh.reactivespring.handler;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

//public class FunctionalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
//    public FunctionalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
//                                              ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
//        super(errorAttributes, new ResourceProperties(), applicationContext);
//        super.setMessageWriters(serverCodecConfigurer.getWriters());
//        super.setMessageReaders(serverCodecConfigurer.getReaders());
//    }
//
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
//        getErrorAttributes(serverRequest, null);
//    }
//}
