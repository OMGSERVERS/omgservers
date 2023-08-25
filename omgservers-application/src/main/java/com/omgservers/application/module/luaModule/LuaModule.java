package com.omgservers.application.module.luaModule;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.HandlerHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;

public interface LuaModule {
    RuntimeHelpService getRuntimeHelpService();

    HandlerHelpService getHandlerHelpService();
}
