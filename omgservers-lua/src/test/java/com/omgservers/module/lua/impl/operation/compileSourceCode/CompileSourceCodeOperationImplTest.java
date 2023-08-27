package com.omgservers.module.lua.impl.operation.compileSourceCode;

import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class CompileSourceCodeOperationImplTest extends Assertions {

    @Inject
    CompileSourceCodeOperation compileSourceCodeOperation;

    @Inject
    CreateServerGlobalsOperation createServerGlobalsOperation;

    @Test
    void givenSourceCode_whenCompileSourceCode_thenCompiled() {
        final var globals = createServerGlobalsOperation.createServerGlobals();
        final var bytes = compileSourceCodeOperation
                .compileSourceCode(globals, "stub", "print('helloworld')");
        assertNotNull(bytes);
    }
}