package com.omgservers.application.module.luaModule.impl;

import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.HandlerHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LuaModuleImpl implements LuaModule {

    final RuntimeHelpService runtimeHelpService;
    final HandlerHelpService handlerHelpService;

    @Override
    public RuntimeHelpService getRuntimeHelpService() {
        return runtimeHelpService;
    }

    @Override
    public HandlerHelpService getHandlerHelpService() {
        return handlerHelpService;
    }
}
