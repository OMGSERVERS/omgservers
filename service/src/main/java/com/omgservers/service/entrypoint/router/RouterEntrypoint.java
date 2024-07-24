package com.omgservers.service.entrypoint.router;

import com.omgservers.service.entrypoint.router.impl.service.routerService.RouterService;

public interface RouterEntrypoint {
    RouterService getRouterService();
}
