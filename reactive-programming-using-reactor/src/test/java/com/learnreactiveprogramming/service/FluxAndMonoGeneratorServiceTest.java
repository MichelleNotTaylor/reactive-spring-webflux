package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.test.StepVerifier;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class FluxAndMonoGeneratorServiceTest {
    @Mock
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService;

    @Test
    void nameFlux() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux();

        StepVerifier.create(nameFlux)
                .expectNext("Michelle", "Taylor")
                .verifyComplete();
    }

    @Test
    void nameMono() {
        var nameMono = fluxAndMonoGeneratorService.nameMono();

        StepVerifier.create(nameMono)
                .expectNext("Michelle")
                .verifyComplete();
    }

    @Test
    void  nameFlux_map() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_map(6);

        StepVerifier.create(nameFlux)
                .expectNext("8 - MICHELLE")
                .verifyComplete();
    }

    @Test
    void nameFlux_immutable() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_immutable();

        StepVerifier.create(nameFlux)
                .expectNext("MICHELLE", "TAYLOR")
                .verifyComplete();
    }


    @Test
    void  nameFlux_flatMap() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_flatMap(6);

        StepVerifier.create(nameFlux)
                .expectNext("M", "I", "C", "H", "E", "L", "L", "E")
                .verifyComplete();
    }

    @Test
    void nameFlux_flatMap_async() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_flatMap_async(6);

        StepVerifier.create(nameFlux)
                .expectNext("M", "I", "C", "H", "E", "L", "L", "E")
                .verifyComplete();
    }

    @Test
    void nameFlux_flatMap_concatmap() {
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_flatMap_async(6);

        StepVerifier.create(nameFlux)
                .expectNext("M", "I", "C", "H", "E", "L", "L", "E")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        int stringLength = 7;
        var value = fluxAndMonoGeneratorService.namesMono_flatMap(stringLength);
        StepVerifier.create(value)
                .expectNext(List.of("Michelle"))
                .verifyComplete();
    }
}