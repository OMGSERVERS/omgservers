package com.omgservers.service.shard.client.impl.service.clientService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class ClientServiceTest extends BaseTestClass {

    @Inject
    ClientService clientService;
}