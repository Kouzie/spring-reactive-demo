package com.example.react.practice;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleExample {

    // tag::simple-example[]


    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        List<Integer> source;
        if (new Random().nextBoolean()) {
            source = IntStream.range(1, 11).boxed().collect(Collectors.toList());
        } else {
            source = Arrays.asList(1, 2, 3, 4);
        }

        try {
            executor.submit(() -> source.get(5)).get(); // line 52
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

}