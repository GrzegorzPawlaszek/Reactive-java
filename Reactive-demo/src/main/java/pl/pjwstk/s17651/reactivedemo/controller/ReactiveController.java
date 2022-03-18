package pl.pjwstk.s17651.reactivedemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.s17651.reactivedemo.domain.FootballMatch;
import pl.pjwstk.s17651.reactivedemo.service.FootballMatchService;
import pl.pjwstk.s17651.reactivedemo.utils.Constants;
import reactor.core.publisher.Flux;

import static pl.pjwstk.s17651.reactivedemo.utils.Constants.TEST_ENDPOINT;


@RestController
@RequestMapping("/reactive")
@RequiredArgsConstructor
public class ReactiveController {

    private final FootballMatchService service;
    
    @GetMapping(value = Constants.FIND_ALL_ENDPOINT, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FootballMatch> findAll() {
        return service.findAllMatches();
    }

    @GetMapping(value = Constants.FIND_ALL_PAGED_ENDPOINT, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FootballMatch> findAllWithPaging(final @RequestParam(name = "page") int page,
                                                 final @RequestParam(name = "size") int size) {
        return service.findAllMatches(page, size);
    }

    @GetMapping(value = TEST_ENDPOINT)
    public String test() {
        return "Test response from ReactiveController";
    }
}