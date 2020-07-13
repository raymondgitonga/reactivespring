package com.tosh.reactivespring;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFatcoryTest {

    List<String> names = Arrays.asList("Tosh", "Tooti", "Steph", "Jack");

    @Test
    public void fluxUsingIterable(){
        Flux<String> namesFlux = Flux.fromIterable(names);

        StepVerifier.create(namesFlux)
                .expectNext("Tosh", "Tooti", "Steph", "Jack")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray(){
        String [] names = new String[]{"Tosh", "Tooti", "Steph", "Jack"};

        Flux<String> namesFlux = Flux.fromArray(names);

        StepVerifier.create(namesFlux)
                .expectNext("Tosh", "Tooti", "Steph", "Jack")
                .verifyComplete();
    }
    @Test
    public void fluxUsingStream(){
        Flux<String> namesFlux = Flux.fromStream(names.stream());

        StepVerifier.create(namesFlux)
                .expectNext("Tosh", "Tooti", "Steph", "Jack")
                .verifyComplete();
    }

    @Test
    public void moniUsingJustOrEmpty(){
       Mono<String> monoString =  Mono.justOrEmpty(null);

       StepVerifier.create(monoString)
               .verifyComplete();
    }

    @Test
    public void moniUsingSupplier(){
        Supplier<String> stringSupplier = () -> "Tosh";

        Mono<String> mono = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(mono)
                .expectNext("Tosh")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange(){
        Flux<Integer> fluxInteger = Flux.range(1, 5);

        StepVerifier.create(fluxInteger)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }

}
