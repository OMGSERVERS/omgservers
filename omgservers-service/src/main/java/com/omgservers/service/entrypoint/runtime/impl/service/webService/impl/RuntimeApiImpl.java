package com.omgservers.service.entrypoint.runtime.impl.service.webService.impl;

import com.omgservers.api.RuntimeApi;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.RUNTIME})
class RuntimeApiImpl implements RuntimeApi {

    final WebService webService;

    @Override
    @PermitAll
    public Uni<CreateTokenRuntimeResponse> execute(@NotNull final CreateTokenRuntimeRequest request) {
        return webService.execute(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.RUNTIME})
    public Uni<InterchangeMessagesRuntimeResponse> execute(@NotNull final InterchangeMessagesRuntimeRequest request) {
        return webService.execute(request);
    }
}
