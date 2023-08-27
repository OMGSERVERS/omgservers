package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.deleteTenant;

import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.module.tenant.impl.operation.deleteTenant.DeleteTenantOperation;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
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
    public Uni<Void> deleteTenant(final DeleteTenantShardedRequest request) {
        DeleteTenantShardedRequest.validate(request);

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
