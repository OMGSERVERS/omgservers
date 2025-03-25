package com.omgservers.service.shard.alias.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.alias.service.testInterface.AliasServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class AliasServiceTest extends BaseTestClass {

    @Inject
    AliasServiceTestInterface aliasService;
}