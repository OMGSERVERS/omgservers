package com.omgservers.service.master.node.impl.service.webService.impl.api;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.master.node.impl.service.webService.WebService;
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
public class NodeApiImpl implements NodeApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<AcquireNodeResponse> execute(final AcquireNodeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
