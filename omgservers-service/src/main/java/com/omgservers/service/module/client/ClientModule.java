package com.omgservers.service.module.client;

import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.shortcutService.ShortcutService;

public interface ClientModule {
    ClientService getClientService();

    ShortcutService getShortcutService();
}
