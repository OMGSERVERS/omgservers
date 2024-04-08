package com.omgservers.tester.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;
}
