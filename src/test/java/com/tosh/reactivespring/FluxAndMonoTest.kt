package com.tosh.reactivespring

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class FluxAndMonoTest {
    @Test
    fun fluxTest() {
        val stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.just("After Error"))
                .log()
        stringFlux.subscribe({ x: String? -> println(x) }, { x: Throwable? -> System.err.println(x) }
        ) { println("Completed") }
    }

    @Test
    fun fluxTestElements_WithoutError() {
        val stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log()
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete()
    }

    @Test
    fun fluxTestElements_WithError() {
        val stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .log()
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .expectErrorMessage("Exception Occured")
                .verify()
    }

    @Test
    fun fluxTestElementsCount_WithError() {
        val stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log()
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete()
    }

    @Test
    fun fluxTestElements_WithoutError1() {
        val stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log()
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .verifyComplete()
    }

    @Test
    fun monoTest() {
        val stringMono = Mono.just("Spring")
                .log()
        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete()
    }

    @Test
    fun monoTest_Error() {
        StepVerifier.create(Mono.error<Any>(RuntimeException("Exception Occured")))
                .expectError(RuntimeException::class.java)
                .verify()
    }
}