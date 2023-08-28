package com.omgservers.module.context;

import com.omgservers.module.context.impl.service.handlerService.ContextService;

public interface ContextModule {

    ContextService getContextService();
}
