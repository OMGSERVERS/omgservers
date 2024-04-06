package com.omgservers.service.module.client.impl.service.clientService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class ClientServiceTest extends Assertions {

    @Inject
    ClientService clientService;
}