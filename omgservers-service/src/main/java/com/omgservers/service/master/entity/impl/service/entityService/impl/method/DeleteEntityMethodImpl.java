package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.service.master.entity.impl.operation.DeleteEntityOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteEntityMethodImpl implements DeleteEntityMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteEntityOperation deleteEntityOperation;

    @Override
    public Uni<DeleteEntityResponse> execute(final DeleteEntityRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteEntityOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteEntityResponse::new);
    }
}
