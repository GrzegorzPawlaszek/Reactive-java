package pl.pjwstk.s17651.mvcdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pjwstk.s17651.mvcdemo.domain.FootballMatch;
import pl.pjwstk.s17651.mvcdemo.repository.FootballMatchRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchRepository repository;
    private final SimulateLatencyService latencyService;

    public List<FootballMatch> findAllMatches() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(latencyService::simulateLatency)
                .collect(Collectors.toList());
    }

    public List<FootballMatch> findAllMatches(int page, int size) {
        return repository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size))
                .stream()
                .map(latencyService::simulateLatency)
                .collect(Collectors.toList());
    }
}
