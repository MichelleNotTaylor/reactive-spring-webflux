package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {
    public FluxAndMonoGeneratorService() {
    }

    public Flux<String> nameFlux() {
        return Flux.fromIterable(List.of("Michelle", "Taylor"));
    }

    public Mono<String> nameMono() {
        return Mono.just("Michelle");
    }

    public Flux<String> nameFlux_map(int stringLength) {
        return Flux.fromIterable(List.of("Michelle", "Taylor"))
                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + " - " + s)
                .log();
    }

    public Flux<String> nameFlux_immutable() {
        var nameFlux = Flux.fromIterable(List.of("Michelle", "Taylor"));
        nameFlux.map(String::toUpperCase);
        return nameFlux;
    }

    public Flux<String> nameFlux_flatMap(int stringLength) {
        return Flux.fromIterable(List.of("Michelle", "Taylor"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString)
                .log();
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> nameFlux_flatMap_async(int stringLength) {
        return Flux.fromIterable(List.of("Michelle", "Taylor"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString_withDelay)
                .log();
    }

    public Flux<String> nameFlux_flatMap_concatmap(int stringLength) {
        return Flux.fromIterable(List.of("Michelle", "Taylor"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(this::splitString_withDelay)
                .log();
    }

    public Flux<String> splitString_withDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Mono<List<String>> namesMono_flatMap(int stringLength) {
        return Mono.just("Michelle")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono);
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.nameFlux()
                .subscribe(name -> {
            System.out.println("Name: " + name);
        });

        fluxAndMonoGeneratorService.nameMono().subscribe(name -> {
            System.out.println("Name: " + name);
        });
    }
}
