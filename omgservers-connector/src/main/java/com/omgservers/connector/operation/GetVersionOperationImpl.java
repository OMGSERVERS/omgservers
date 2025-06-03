package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
class GetVersionOperationImpl implements GetVersionOperation {

    final String version;

    public GetVersionOperationImpl(@ConfigProperty(name = "quarkus.application.version") final String version) {
        this.version = version;
    }

    public String execute() {
        return version;
    }
}
