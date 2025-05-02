package com.omgservers.ctl.operation.ctl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
class HandleFileOptionOperationImpl implements HandleFileOptionOperation {

    @Override
    @SneakyThrows
    public String execute(String option) {
        final InputStream inputStream;
        if ("-".equals(option)) {
            inputStream = System.in;
        } else {
            inputStream = new FileInputStream(option);
        }

        final var fileString = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));

        return fileString;
    }
}
