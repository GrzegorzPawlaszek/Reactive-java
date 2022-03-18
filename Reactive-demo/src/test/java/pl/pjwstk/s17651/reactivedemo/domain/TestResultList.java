package pl.pjwstk.s17651.reactivedemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResultList {

    private int numberOfRequests;
    private int numberOfResultsPerRequest;
    private String averageRequestDuration;
    private String totalTestDuration;
    private List<TestResult> testResultList;
}
