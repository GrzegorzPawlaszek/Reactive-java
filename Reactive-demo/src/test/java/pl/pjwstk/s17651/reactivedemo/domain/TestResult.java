package pl.pjwstk.s17651.reactivedemo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class TestResult {

    private final double requestDurationInSeconds;
    private final String response;
}