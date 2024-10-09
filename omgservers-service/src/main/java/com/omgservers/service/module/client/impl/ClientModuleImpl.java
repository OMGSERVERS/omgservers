package com.omgservers.service.module.client.impl;

import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClientModuleImpl implements ClientModule {

    final ClientService clientService;

    @Override
    public ClientService getService() {
        return clientService;
    }
}
