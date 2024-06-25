package com.omgservers.service.module.system.impl.service.indexService.impl;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.indexService.impl.method.deleteIndex.DeleteIndexMethod;
import com.omgservers.service.module.system.impl.service.indexService.impl.method.getIndex.GetIndexMethod;
import com.omgservers.service.module.system.impl.service.indexService.impl.method.syncIndex.SyncIndexMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class IndexServiceImpl implements IndexService {

    final DeleteIndexMethod deleteIndexMethod;
    final SyncIndexMethod syncIndexMethod;
    final GetIndexMethod getIndexMethod;

    @Override
    public Uni<GetIndexResponse> getIndex(@Valid final GetIndexRequest request) {
        return getIndexMethod.getIndex(request);
    }

    @Override
    public Uni<SyncIndexResponse> syncIndex(@Valid final SyncIndexRequest request) {
        return syncIndexMethod.syncIndex(request);
    }

    @Override
    public Uni<SyncIndexResponse> syncIndexWithIdempotency(@Valid final SyncIndexRequest request) {
        return syncIndex(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getIndex(), t.getMessage());
                            return Uni.createFrom().item(new SyncIndexResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteIndexResponse> deleteIndex(@Valid final DeleteIndexRequest request) {
        return deleteIndexMethod.deleteIndex(request);
    }
}
