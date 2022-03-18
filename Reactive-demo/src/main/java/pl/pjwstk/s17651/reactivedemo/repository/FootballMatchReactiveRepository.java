package pl.pjwstk.s17651.reactivedemo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import pl.pjwstk.s17651.reactivedemo.domain.FootballMatch;
import reactor.core.publisher.Flux;

@Repository
public interface FootballMatchReactiveRepository extends ReactiveSortingRepository<FootballMatch, String> {

    Flux<FootballMatch> findAllByIdNotNullOrderByIdAsc(final Pageable page);

}
