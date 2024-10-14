package com.omgservers.service.entrypoint.dispatcher.impl;

import com.omgservers.service.entrypoint.dispatcher.DispatcherEntrypoint;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;
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

    final DispatcherService service;
}
