package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.deleteTenantMethod;

import com.omgservers.application.module.tenantModule.impl.operation.deleteTenantOperation.DeleteTenantOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final InternalModule internalModule;

    final DeleteTenantOperation deleteTenantOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteTenant(final DeleteTenantRoutedRequest request) {
        DeleteTenantRoutedRequest.validate(request);

        final var id = request.getId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        ((sqlConnection, shardModel) -> deleteTenantOperation
                                .deleteTenant(sqlConnection, shardModel.shard(), id)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Tenant was deleted, id=" + id);
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new TenantDeletedEventBodyModel(id);
                            } else {
                                return null;
                            }
                        }))
                .map(ChangeWithEventResponse::getResult)
                //TODO: implement response with deleted flag
                .replaceWithVoid();
    }
}
