package com.omgservers.tester.operation.waitForDeployment;

import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface WaitForDeploymentOperation {

    void waitForDeployment(TestVersionDto testVersion) throws IOException;
}
