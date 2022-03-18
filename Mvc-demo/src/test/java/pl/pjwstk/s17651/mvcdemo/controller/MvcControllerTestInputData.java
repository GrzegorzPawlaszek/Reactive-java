package pl.pjwstk.s17651.mvcdemo.controller;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class MvcControllerTestInputData {

    public static Stream<Arguments> getMvcControllerTestInputData() {
//        requests, data size, thread pool size
        return Stream.of(
//================THREAD POOL SIZE = 32=============================
//                Arguments.of(1,10,32),
//                Arguments.of(32,10,32),
//                Arguments.of(64,10,32),
//                Arguments.of(128,10,32),
//                Arguments.of(256,10,32),
//                Arguments.of(512,10,32)
//                Arguments.of(1,25,32),
//                Arguments.of(32,25,32),
//                Arguments.of(64,25,32),
//                Arguments.of(1,50,32),
//                Arguments.of(32,50,32),
//                Arguments.of(64,50,32),

//================THREAD POOL SIZE = 64=============================
                Arguments.of(1,10,64),
                Arguments.of(32,10,64),
                Arguments.of(64,10,64),
                Arguments.of(128,10,64),
                Arguments.of(256,10,64),
                Arguments.of(512,10,64)
//                Arguments.of(1,25,64),
//                Arguments.of(32,25,64),
//                Arguments.of(64,25,64),
//                Arguments.of(1,50,64),
//                Arguments.of(32,50,64),
//                Arguments.of(64,50,64),
        );
    }
}
