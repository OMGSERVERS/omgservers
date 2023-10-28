package com.omgservers.module.script.impl;

import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.impl.service.scriptService.ScriptService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ScriptModuleImpl implements ScriptModule {

    final ScriptService scriptService;

    public ScriptService getScriptService() {
        return scriptService;
    }
}
