package com.omgservers.application.module.luaModule;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.HandlerHelpService;

public interface LuaModule {
    RuntimeHelpService getRuntimeHelpService();

    HandlerHelpService getHandlerHelpService();
}
