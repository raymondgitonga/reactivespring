package com.tosh.reactivespring.FluxAndMonoPlayGround;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("Tosh", "Tooti", "Steph", "Jack");

    @Test
    public void transformUsingMap(){
        Flux<String> nameFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(nameFlux)
                .expectNext("TOSH", "TOOTI", "STEPH", "JACK")
                .verifyComplete();


    }

    @Test
    public void transformUsingMap_Length(){
        Flux<Integer> nameFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(nameFlux)
                .expectNext(4, 5, 5, 4)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_Repeat(){
        Flux<Integer> nameFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(nameFlux)
                .expectNext(4, 5, 5, 4, 4, 5, 5, 4)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap(){
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A","B", "C", "D", "E", "F"))
                .flatMap(s ->  Flux.fromIterable(converToList(s))) // db or external service call that returns a flux
                .log();

        System.out.println(namesFlux);

        StepVerifier.create(namesFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> converToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Arrays.asList(s, "newValue");
    }

    @Test
    public void transformUsingFlatMap_usingParallel(){
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A","B", "C", "D", "E", "F"))
                .window(2)// returns two items at a time instead of one ie {A,B}
                .flatMap(s -> s.map(this::converToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        System.out.println(namesFlux);

        StepVerifier.create(namesFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_usingParallel_maintain_order(){
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A","B", "C", "D", "E", "F"))
                .window(2)// returns two items at a time instead of one ie {A,B}
                .flatMapSequential(s -> s.map(this::converToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        System.out.println(namesFlux);

        StepVerifier.create(namesFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void convertFluxToInt(){
         int x;

         Mono.just(x = 1)
                 .subscribe();

        System.out.println(x);


    }

}
