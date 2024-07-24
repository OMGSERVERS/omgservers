package com.omgservers.service.entrypoint.router.impl;

import com.omgservers.service.entrypoint.router.RouterEntrypoint;
import com.omgservers.service.entrypoint.router.impl.service.routerService.RouterService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RouterEntrypointImpl implements RouterEntrypoint {

    final RouterService routerService;
}
