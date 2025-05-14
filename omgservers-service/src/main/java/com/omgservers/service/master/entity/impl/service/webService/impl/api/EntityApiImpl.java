package com.omgservers.service.master.entity.impl.service.webService.impl.api;

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
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.master.entity.impl.service.webService.WebService;
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
public class EntityApiImpl implements EntityApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetEntityResponse> execute(final GetEntityRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindEntityResponse> execute(final FindEntityRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewEntitiesResponse> execute(final ViewEntitiesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncEntityResponse> execute(final SyncEntityRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteEntityResponse> execute(final DeleteEntityRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
