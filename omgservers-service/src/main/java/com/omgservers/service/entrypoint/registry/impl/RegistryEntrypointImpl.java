package com.omgservers.service.entrypoint.registry.impl;

import com.omgservers.service.entrypoint.registry.RegistryEntrypoint;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RegistryEntrypointImpl implements RegistryEntrypoint {

    final RegistryService registryService;
}
