package com.omgservers.service.module.system.impl.service.entityService.impl;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.service.module.system.impl.service.entityService.EntityService;
import com.omgservers.service.module.system.impl.service.entityService.impl.method.deleteEntity.DeleteEntityMethod;
import com.omgservers.service.module.system.impl.service.entityService.impl.method.findEntity.FindEntityMethod;
import com.omgservers.service.module.system.impl.service.entityService.impl.method.syncEntity.SyncEntityMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EntityServiceImpl implements EntityService {

    final DeleteEntityMethod deleteEntityMethod;
    final SyncEntityMethod syncEntityMethod;
    final FindEntityMethod findEntityMethod;

    @Override
    public Uni<FindEntityResponse> findEntity(@Valid final FindEntityRequest request) {
        return findEntityMethod.findEntity(request);
    }

    @Override
    public Uni<SyncEntityResponse> syncEntity(@Valid final SyncEntityRequest request) {
        return syncEntityMethod.syncEntity(request);
    }

    @Override
    public Uni<DeleteEntityResponse> deleteEntity(@Valid final DeleteEntityRequest request) {
        return deleteEntityMethod.deleteEntity(request);
    }
}
