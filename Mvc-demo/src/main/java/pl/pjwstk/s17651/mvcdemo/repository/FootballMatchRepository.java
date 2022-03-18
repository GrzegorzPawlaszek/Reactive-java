package pl.pjwstk.s17651.mvcdemo.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.pjwstk.s17651.mvcdemo.domain.FootballMatch;

import java.util.List;

public interface FootballMatchRepository extends PagingAndSortingRepository<FootballMatch, Long> {

    List<FootballMatch> findAllByIdNotNullOrderByIdAsc(PageRequest of);
}
