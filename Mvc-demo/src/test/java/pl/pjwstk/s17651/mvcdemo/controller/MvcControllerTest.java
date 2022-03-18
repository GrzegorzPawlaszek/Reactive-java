package pl.pjwstk.s17651.mvcdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.pjwstk.s17651.mvcdemo.domain.TestResult;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.pjwstk.s17651.mvcdemo.utils.Constants.*;
import static pl.pjwstk.s17651.mvcdemo.utils.TimeParser.getDurationInSeconds;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MvcControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @DisplayName("MVC endpoint performance test")
    @ParameterizedTest
    @MethodSource("pl.pjwstk.s17651.mvcdemo.controller.MvcControllerTestInputData#getMvcControllerTestInputData")
    public void startTest(int requests, int size, int threadPoolSize) throws Exception {
        long startTime = System.nanoTime();

        List<Callable<TestResult>> requestCallableList = getRequestCallableList(requests, size);
        ExecutorCompletionService<TestResult> executorCompletionService = getExecutorCompletionService(requestCallableList, threadPoolSize);

        List<TestResult> testResults = collectTestResults(executorCompletionService, requestCallableList.size());

        double averageDuration = testResults.stream().mapToDouble(r -> r.getRequestDurationInSeconds()).average().getAsDouble();

        log.info("******* MVC ENDPOINT TEST SUMMARY ********");
        log.info("Number of requests: {}, Number of results per request: {}, Thread pool size: {}", requests, size, threadPoolSize);
        log.info("Average request duration: {} sec.", averageDuration);
        log.info("Test duration: {} sec.", getDurationInSeconds(startTime));
    }

    private ExecutorCompletionService<TestResult> getExecutorCompletionService(List<Callable<TestResult>> requestCallableList, int threadPoolSize) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        ExecutorCompletionService<TestResult> executorCompletionService = new ExecutorCompletionService<>(executorService);
        requestCallableList.forEach(executorCompletionService::submit);

        return executorCompletionService;
    }

    private List<TestResult> collectTestResults(ExecutorCompletionService<TestResult> executorCompletionService, int size) throws InterruptedException, ExecutionException {
        return IntStream.range(0, size)
                .mapToObj(n ->
                        getResult(executorCompletionService)
                ).collect(Collectors.toList());
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

    private List<Callable<TestResult>> getRequestCallableList(int requests, int size) {
        return IntStream.range(0, requests)
                .mapToObj(i -> getRequest(size))
                .collect(Collectors.toList());
    }

    private Callable<TestResult> getRequest(int size) {
        return () -> {
            long startTime = System.nanoTime();
            String url = BASE_URL + ":" + port + APP_URL + FIND_ALL_PAGED_ENDPOINT + "?page=0&size=" + size;
            ResponseEntity<String> responseEntity
                    = restTemplate.getForEntity(url, String.class);

            return TestResult.builder()
                    .requestDurationInSeconds(getDurationInSeconds(startTime))
                    .response(responseEntity.getBody())
                    .build();
        };
    }
}
