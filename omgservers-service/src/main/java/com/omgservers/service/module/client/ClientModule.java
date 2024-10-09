package com.omgservers.service.module.client;

import com.omgservers.service.module.client.impl.service.clientService.ClientService;

public interface ClientModule {
    ClientService getService();
}
