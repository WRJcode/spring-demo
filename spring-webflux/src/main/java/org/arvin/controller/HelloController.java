package org.arvin.controller;

import org.assertj.core.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/webflux")
    public Mono<String> webflux() {
        return Mono.just("Welcome to webflux world ~");
    }

    @GetMapping("/flux/test")
    public Flux<String> flux(){
        String[] strings = Arrays.array("hello","welcome","world","we","are","family");
        return Flux.just(strings);
    }
}
