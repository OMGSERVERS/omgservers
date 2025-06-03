package com.omgservers.connector.entrypoint.impl;

import com.omgservers.connector.entrypoint.ConnectorEntrypoint;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.EntrypointService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ConnectorEntrypointImpl implements ConnectorEntrypoint {

    final EntrypointService entrypointService;
}
