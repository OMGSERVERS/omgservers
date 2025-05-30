package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@QuarkusTest
public class CalculateCrc16OperationTest extends BaseTestClass {

    @Inject
    CalculateCrc16Operation calculateCrc16Operation;

    @Test
    void testBasedOnUuid() {
        final var iterations = 10_000_000;
        final var buckets = 16;

        final var testMap = new TestMap<UUID>(buckets);

        for (int i = 0; i < iterations; i++) {
            UUID uuid = UUID.randomUUID();
            byte[] bytes = uuid.toString().getBytes(StandardCharsets.UTF_8);
            testMap.put(bytes);
        }

        testMap.check();
    }

    @Test
    void testBasedOnNumbers() {
        final var iterations = 100_000_000;
        final var buckets = 16;

        final var testMap = new TestMap<UUID>(buckets);

        for (int i = 0; i < iterations; i++) {
            byte[] bytes = Integer.valueOf(i).toString().getBytes(StandardCharsets.UTF_8);
            testMap.put(bytes);
        }

        testMap.check();
    }

    @Test
    void testBasedOnWords() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/words.txt");
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        var lines = new ArrayList();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        final List<String> words = lines.stream().distinct().toList();
        final var buckets = 16;

        final var testMap = new TestMap<UUID>(buckets);

        for (var word : words) {
            byte[] bytes = word.getBytes(StandardCharsets.UTF_8);
            testMap.put(bytes);
        }

        testMap.check();
    }

    class TestMap<T> {
        final int buckets;
        final List<AtomicLong> distribution;

        TestMap(int buckets) {
            this.buckets = buckets;
            distribution = new ArrayList<>(buckets);
            for (int i = 0; i < buckets; i++) {
                distribution.add(new AtomicLong());
            }
        }

        void put(byte[] bytes) {
            Integer crc16 = calculateCrc16Operation.execute(bytes);
            Integer index = crc16 % buckets;
            distribution.get(index).incrementAndGet();
        }

        void check() {
            final var average = distribution.stream()
                    .mapToLong(AtomicLong::get)
                    .average().getAsDouble();
            distribution.stream()
                    .mapToLong(AtomicLong::get)
                    .mapToDouble(value -> {
                        var result = Math.abs(value - average) / average * 100;
                        return result;
                    })
                    .forEach(value -> {
                        assertTrue(value < 10);
                    });
        }
    }
}
