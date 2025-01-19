package com.omgservers.service.service.index.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.service.index.IndexService;
import com.omgservers.service.service.index.dto.DeleteIndexRequest;
import com.omgservers.service.service.index.dto.DeleteIndexResponse;
import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
import com.omgservers.service.service.index.dto.SyncIndexRequest;
import com.omgservers.service.service.index.dto.SyncIndexResponse;
import com.omgservers.service.service.index.impl.method.deleteIndex.DeleteIndexMethod;
import com.omgservers.service.service.index.impl.method.getIndex.GetIndexMethod;
import com.omgservers.service.service.index.impl.method.syncIndex.SyncIndexMethod;
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
                            log.debug("Idempotency was violated, object={}, {}", request.getIndex(), t.getMessage());
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
