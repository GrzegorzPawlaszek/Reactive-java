package pl.pjwstk.s17651.reactivedemo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.pjwstk.s17651.reactivedemo.domain.FootballMatch;
import pl.pjwstk.s17651.reactivedemo.repository.FootballMatchReactiveRepository;
import pl.pjwstk.s17651.reactivedemo.utils.MatchCsvParser;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static pl.pjwstk.s17651.reactivedemo.utils.Constants.RESULTS_CSV;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("prod")
public class MongoDbDataLoader implements ApplicationRunner {

    private final FootballMatchReactiveRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (isDatabaseEmpty()) {
            File file = File.createTempFile("output", "csv");
            ClassPathResource classPathResource = new ClassPathResource(RESULTS_CSV);
            try (InputStream inputStream = classPathResource.getInputStream()){
                 FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            List<FootballMatch> footballMatchList = MatchCsvParser.parseFootballMatches(file);

            Flux.fromIterable(footballMatchList)
                    .map(repository::save)
                    .subscribe(footballMatch -> log.info("Football match saved: " + footballMatch.block()));

            log.info("All data uploaded. Inserted {} Football matches.", repository.count().block());
        }
    }

    private boolean isDatabaseEmpty() {
        return repository.count().block() == 0L;
    }
}
