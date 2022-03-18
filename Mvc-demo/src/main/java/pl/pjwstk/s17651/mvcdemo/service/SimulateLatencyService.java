package pl.pjwstk.s17651.mvcdemo.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.pjwstk.s17651.mvcdemo.domain.FootballMatch;
import pl.pjwstk.s17651.mvcdemo.utils.Constants;

@Service
public class SimulateLatencyService {

    @SneakyThrows
    public FootballMatch simulateLatency(FootballMatch footballMatch) {
        Thread.sleep(Constants.LATENCY_IN_MILLIS);
        return footballMatch;
    }
}
