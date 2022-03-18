package pl.pjwstk.s17651.mvcdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pjwstk.s17651.mvcdemo.domain.FootballMatch;
import pl.pjwstk.s17651.mvcdemo.service.FootballMatchService;
import pl.pjwstk.s17651.mvcdemo.utils.Constants;

import java.util.List;

import static pl.pjwstk.s17651.mvcdemo.utils.Constants.APP_URL;

@RestController
@RequestMapping(APP_URL)
@RequiredArgsConstructor
public class McvController {

    private final FootballMatchService service;

    @GetMapping(value = Constants.FIND_ALL_ENDPOINT)
    public List<FootballMatch> findAll() {
        return service.findAllMatches();
    }

    @GetMapping(Constants.FIND_ALL_PAGED_ENDPOINT)
    public List<FootballMatch> findAllWithPaging(final @RequestParam(name = "page") int page,
                                                 final @RequestParam(name = "size") int size) {
        return service.findAllMatches(page, size);
    }

    @GetMapping(value = "test")
    public String ping() {
        return "Test response from McvController";
    }
}