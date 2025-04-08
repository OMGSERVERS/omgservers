package com.omgservers;

import com.omgservers.service.service.task.impl.method.executeBootstrapTask.BootstrapTaskArguments;
import com.omgservers.service.service.task.impl.method.executeBootstrapTask.BootstrapTaskImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
@QuarkusTest
public class BaseTestClass extends Assertions {

    @Inject
    BootstrapTaskImpl bootstrapTask;

    @BeforeEach
    void beforeEach() {
        bootstrapTask.execute(new BootstrapTaskArguments())
                .await().indefinitely();
    }
}
