package com.omgservers.module.tenant.impl.operation.deleteVersionRuntime;

import com.omgservers.model.event.body.StageRuntimeDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionRuntimeOperationImpl implements DeleteVersionRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteVersionRuntime(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long tenantId,
                                             final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_tenant_version_runtime
                        set deleted = true
                        where tenant_id = $1 and id = $3 and deleted = false
                        """,
                Arrays.asList(tenantId, id),
                () -> new StageRuntimeDeletedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Version runtime was deleted, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
