package com.omgservers.service.master.entity.impl;

import com.omgservers.service.master.entity.EntityMaster;
import com.omgservers.service.master.entity.impl.service.entityService.EntityService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EntityMasterImpl implements EntityMaster {

    final EntityService entityService;

    @Override
    public EntityService getService() {
        return entityService;
    }
}
