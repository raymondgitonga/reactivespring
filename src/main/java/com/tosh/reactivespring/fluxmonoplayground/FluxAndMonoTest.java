package com.tosh.reactivespring.fluxmonoplayground;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.just("After Error"))
                .log();

        stringFlux.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
        .expectNext("Spring")
        .expectNext("Spring Boot")
        .expectNext("Reactive Spring")
        .verifyComplete();

    }

    @Test
    public void fluxTestElements_WithError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .expectErrorMessage("Exception Occured")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithoutError1(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest(){
       Mono<String> stringMono =  Mono.just("Spring")
               .log();

       StepVerifier.create(stringMono)
               .expectNext("Spring")
               .verifyComplete();
    }

    @Test
    public void monoTest_Error(){

        StepVerifier.create(Mono.error(new RuntimeException("Exception Occured")))
                .expectError(RuntimeException.class)
                .verify();
    }
}