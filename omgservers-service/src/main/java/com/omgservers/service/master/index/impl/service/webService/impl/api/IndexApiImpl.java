package com.omgservers.service.master.index.impl.service.webService.impl.api;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.master.index.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({UserRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IndexApiImpl implements IndexApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetIndexResponse> execute(final GetIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncIndexResponse> execute(final SyncIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
