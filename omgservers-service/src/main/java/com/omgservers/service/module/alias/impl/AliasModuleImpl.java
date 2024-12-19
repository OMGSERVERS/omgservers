package com.omgservers.service.module.alias.impl;

import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.alias.impl.service.aliasService.AliasService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AliasModuleImpl implements AliasModule {

    final AliasService aliasService;

    @Override
    public AliasService getService() {
        return aliasService;
    }
}
