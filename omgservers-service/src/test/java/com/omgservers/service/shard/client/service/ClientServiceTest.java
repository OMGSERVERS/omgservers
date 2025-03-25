package com.omgservers.service.shard.client.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.client.impl.service.clientService.ClientService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class ClientServiceTest extends BaseTestClass {

    @Inject
    ClientService clientService;
}