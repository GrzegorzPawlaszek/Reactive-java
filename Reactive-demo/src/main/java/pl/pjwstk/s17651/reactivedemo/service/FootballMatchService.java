package pl.pjwstk.s17651.reactivedemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pjwstk.s17651.reactivedemo.domain.FootballMatch;
import pl.pjwstk.s17651.reactivedemo.repository.FootballMatchReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchReactiveRepository repository;
    private final SimulateLatencyService latencyService;

    public Flux<FootballMatch> findAllMatches() {
        return repository.findAll()
                .subscribeOn(Schedulers.boundedElastic())
                .map(latencyService::simulateLatency);
    }

    public Flux<FootballMatch> findAllMatches(int page, int size) {
        return repository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size))
                .subscribeOn(Schedulers.boundedElastic())
                .map(latencyService::simulateLatency);

    }
}