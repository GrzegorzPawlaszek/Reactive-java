package pl.pjwstk.s17651.reactivedemo.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import pl.pjwstk.s17651.reactivedemo.domain.FootballMatch;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class MatchCsvParser {

    @SneakyThrows
    public static List<FootballMatch> parseFootballMatches(File resourceFile) {
        List<FootballMatch> footballMatchList = new CsvToBeanBuilder(new FileReader(resourceFile))
                .withType(FootballMatch.class)
                .build()
                .parse();

        footballMatchList.forEach(match ->
                match.setId(MatchIdGenerator.generateMatchNextId()));

        return footballMatchList;
    }

    @SneakyThrows
    public static List<FootballMatch> parseFootballMatches2(File resourceFile) {
        List<FootballMatch> footballMatchList = new CsvToBeanBuilder(new FileReader(resourceFile))
                .withType(FootballMatch.class)
                .build()
                .parse();

        footballMatchList.forEach(match ->
                match.setId(MatchIdGenerator.generateMatchNextId()));

        return footballMatchList;
    }
}
