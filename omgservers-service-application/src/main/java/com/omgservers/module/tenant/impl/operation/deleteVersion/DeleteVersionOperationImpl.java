package com.omgservers.module.tenant.impl.operation.deleteVersion;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.selectVersion.SelectVersionOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionOperationImpl implements DeleteVersionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectVersionOperation selectVersionOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteVersion(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final Long id) {
        return selectVersionOperation.selectVersion(sqlConnection, shard, tenantId, id)
                .flatMap(version -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_tenant_version
                                where tenant_id = $1 and id = $2
                                """,
                        Arrays.asList(tenantId, id),
                        () -> new VersionDeletedEventBodyModel(version),
                        () -> logModelFactory.create("Version was deleted, version=" + version)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
