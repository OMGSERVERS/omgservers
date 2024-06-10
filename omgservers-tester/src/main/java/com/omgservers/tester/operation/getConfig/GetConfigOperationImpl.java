package com.omgservers.tester.operation.getConfig;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConfigOperationImpl implements GetConfigOperation {

    final TesterConfig testerConfig;

    public TesterConfig.EnvironmentConfig getConfig() {
        return switch (testerConfig.environment()) {
            case DEVELOPMENT -> testerConfig.development();
            case INTEGRATION -> testerConfig.integration();
            case STANDALONE -> testerConfig.standalone();
        };
    }
}
