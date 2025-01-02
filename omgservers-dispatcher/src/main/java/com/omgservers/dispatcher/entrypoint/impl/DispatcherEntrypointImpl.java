package com.omgservers.dispatcher.entrypoint.impl;

import com.omgservers.dispatcher.entrypoint.DispatcherEntrypoint;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.EntrypointService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherEntrypointImpl implements DispatcherEntrypoint {

    final EntrypointService entrypointService;
}
