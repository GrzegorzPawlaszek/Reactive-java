package pl.pjwstk.s17651.reactivedemo.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FootballMatch {

    private Long id;
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 0)
    private LocalDate date;
    @CsvBindByPosition(position = 1)
    private String homeTeam;
    @CsvBindByPosition(position = 2)
    private String awayTeam;
    @CsvBindByPosition(position = 3)
    private Integer homeScore;
    @CsvBindByPosition(position = 4)
    private Integer awayScore;
    @CsvBindByPosition(position = 5)
    private String tournament;
    @CsvBindByPosition(position = 6)
    private String city;
    @CsvBindByPosition(position = 7)
    private String country;
    @CsvBindByPosition(position = 8)
    private Boolean neutral;
}