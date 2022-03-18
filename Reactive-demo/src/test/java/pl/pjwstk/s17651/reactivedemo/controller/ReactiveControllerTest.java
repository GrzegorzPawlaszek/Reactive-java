package pl.pjwstk.s17651.reactivedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.s17651.reactivedemo.domain.TestResult;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.pjwstk.s17651.reactivedemo.utils.Constants.*;
import static pl.pjwstk.s17651.reactivedemo.utils.TimeParser.getDurationInSeconds;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReactiveControllerTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeAll
    void setup() {
        webClient = WebClient.create(BASE_URL  + ":" + port + APP_URL);
    }

    @DisplayName("Reactive endpoint performance test")
    @ParameterizedTest
    @MethodSource("pl.pjwstk.s17651.reactivedemo.controller.ReactiveControllerTestInputData#getReactiveControllerTestInputData")
    public void startTest(int requests, int size, int threadPoolSize) {
        long startTime = System.nanoTime();

        List<Callable<TestResult>> requestCallableList = getRequestCallableList(requests, size);
        ExecutorCompletionService<TestResult> executorCompletionService = getExecutorCompletionService(requestCallableList, threadPoolSize);

        List<TestResult> testResults = collectTestResults(executorCompletionService, requestCallableList.size());

        double averageDuration = testResults.stream().mapToDouble(r -> r.getRequestDurationInSeconds()).average().getAsDouble();

        log.info("******* REACTIVE ENDPOINT TEST SUMMARY ********");
        log.info("Number of requests: {}, Number of results per request: {}, Thread pool size: {}", requests, size, threadPoolSize);
        log.info("Average request duration: {} sec.", averageDuration);
        log.info("Test duration: {} sec.", getDurationInSeconds(startTime));
    }

    private List<TestResult> collectTestResults(ExecutorCompletionService<TestResult> executorCompletionService, int size) {
        return IntStream.range(0, size)
                .mapToObj(n ->
                    getResult(executorCompletionService))
                .collect(Collectors.toList());
    }

    private TestResult getResult(ExecutorCompletionService<TestResult> executorCompletionService) {
        TestResult result = null;
        try {
            result = executorCompletionService.take().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ExecutorCompletionService<TestResult> getExecutorCompletionService(List<Callable<TestResult>> requestCallableList, int threadPoolSize) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        ExecutorCompletionService<TestResult> executorCompletionService = new ExecutorCompletionService<>(executorService);
        requestCallableList.forEach(executorCompletionService::submit);

        return executorCompletionService;
    }

    private List<Callable<TestResult>> getRequestCallableList(int requests, int size) {
        return IntStream.range(0, requests)
                .mapToObj(i -> getRequest(size))
                .collect(Collectors.toList());
    }

    private Callable<TestResult> getRequest(int size) {
        return () -> {
            long startTime = System.nanoTime();
            ResponseEntity<String> responseEntity = webClient
                    .get()
                    .uri(FIND_ALL_PAGED_ENDPOINT + "?page=0&size=" + size)
                    .exchange()
                    .block()
                    .toEntity(String.class)
                    .block();

            return TestResult.builder()
                    .requestDurationInSeconds(getDurationInSeconds(startTime))
                    .response(responseEntity.getBody())
                    .build();
        };
    }

}
