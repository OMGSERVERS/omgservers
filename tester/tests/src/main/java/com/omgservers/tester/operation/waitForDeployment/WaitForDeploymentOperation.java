package com.omgservers.tester.operation.waitForDeployment;

import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface WaitForDeploymentOperation {

    void waitForDeployment(TestVersionModel testVersion) throws IOException;
}
