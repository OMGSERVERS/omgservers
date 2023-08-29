package com.omgservers.module.context;

import com.omgservers.module.context.impl.service.contextService.ContextService;

public interface ContextModule {

    ContextService getContextService();
}
