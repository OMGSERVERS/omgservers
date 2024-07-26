package com.omgservers.service.entrypoint.registry;

import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;

public interface RegistryEntrypoint {

    RegistryService getRegistryService();
}