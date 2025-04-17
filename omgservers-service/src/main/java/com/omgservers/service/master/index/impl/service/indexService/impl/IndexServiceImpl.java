package com.omgservers.service.master.index.impl.service.indexService.impl;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.master.index.impl.operation.GetIndexMasterClientOperation;
import com.omgservers.service.master.index.impl.service.indexService.IndexService;
import com.omgservers.service.master.index.impl.service.indexService.impl.method.GetIndexMethod;
import com.omgservers.service.master.index.impl.service.indexService.impl.method.SyncIndexMethod;
import com.omgservers.service.master.index.impl.service.webService.impl.api.IndexApi;
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
class IndexServiceImpl implements IndexService {

    final SyncIndexMethod syncIndexMethod;
    final GetIndexMethod getIndexMethod;

    final GetIndexMasterClientOperation getIndexMasterClientOperation;
    final HandleMasterRequestOperation handleMasterRequestOperation;

    public Uni<GetIndexResponse> execute(@Valid final GetIndexRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getIndexMasterClientOperation::getClient,
                IndexApi::execute,
                getIndexMethod::execute);
    }

    public Uni<SyncIndexResponse> execute(@Valid final SyncIndexRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getIndexMasterClientOperation::getClient,
                IndexApi::execute,
                syncIndexMethod::execute);
    }

    public Uni<SyncIndexResponse> executeWithIdempotency(@Valid final SyncIndexRequest request) {
        return execute(request)
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
}
