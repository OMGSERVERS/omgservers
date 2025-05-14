package com.omgservers.service.master.entity.impl.service.entityService.impl;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.master.entity.impl.operation.GetEntityMasterClientOperation;
import com.omgservers.service.master.entity.impl.service.entityService.EntityService;
import com.omgservers.service.master.entity.impl.service.entityService.impl.method.DeleteEntityMethod;
import com.omgservers.service.master.entity.impl.service.entityService.impl.method.FindEntityMethod;
import com.omgservers.service.master.entity.impl.service.entityService.impl.method.GetEntityMethod;
import com.omgservers.service.master.entity.impl.service.entityService.impl.method.SyncEntityMethod;
import com.omgservers.service.master.entity.impl.service.entityService.impl.method.ViewEntitiesMethod;
import com.omgservers.service.master.entity.impl.service.webService.impl.api.EntityApi;
import com.omgservers.service.operation.server.HandleMasterRequestOperation;
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
    final ViewEntitiesMethod viewEntitiesMethod;
    final FindEntityMethod findEntityMethod;
    final SyncEntityMethod syncEntityMethod;
    final GetEntityMethod getEntityMethod;

    final GetEntityMasterClientOperation getEntityMasterClientOperation;
    final HandleMasterRequestOperation handleMasterRequestOperation;

    @Override
    public Uni<GetEntityResponse> execute(@Valid final GetEntityRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getEntityMasterClientOperation::execute,
                EntityApi::execute,
                getEntityMethod::execute);
    }

    @Override
    public Uni<FindEntityResponse> execute(@Valid final FindEntityRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getEntityMasterClientOperation::execute,
                EntityApi::execute,
                findEntityMethod::execute);
    }

    @Override
    public Uni<ViewEntitiesResponse> execute(@Valid final ViewEntitiesRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getEntityMasterClientOperation::execute,
                EntityApi::execute,
                viewEntitiesMethod::execute);
    }

    @Override
    public Uni<SyncEntityResponse> execute(@Valid final SyncEntityRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getEntityMasterClientOperation::execute,
                EntityApi::execute,
                syncEntityMethod::execute);
    }

    @Override
    public Uni<SyncEntityResponse> executeWithIdempotency(@Valid final SyncEntityRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getEntity(), t.getMessage());
                            return Uni.createFrom().item(new SyncEntityResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteEntityResponse> execute(@Valid final DeleteEntityRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getEntityMasterClientOperation::execute,
                EntityApi::execute,
                deleteEntityMethod::execute);
    }
}
