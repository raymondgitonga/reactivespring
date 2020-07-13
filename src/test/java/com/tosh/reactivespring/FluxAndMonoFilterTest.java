package com.tosh.reactivespring;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {
    List<String> names = Arrays.asList("Tosh", "Tooti", "Steph", "Jack");

    @Test
    public void filterTest(){
        Flux<String> fluxString = Flux.fromIterable(names)
                .filter(s -> s.startsWith("Tosh"));

        StepVerifier.create(fluxString)
                .expectNext("Tosh")
                .verifyComplete();
    }
}
