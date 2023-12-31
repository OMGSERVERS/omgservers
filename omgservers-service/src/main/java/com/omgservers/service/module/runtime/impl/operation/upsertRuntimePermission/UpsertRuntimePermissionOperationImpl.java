package com.omgservers.service.module.runtime.impl.operation.upsertRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimePermissionOperationImpl implements UpsertRuntimePermissionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertRuntimePermission(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final RuntimePermissionModel permission) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_permission(
                            id, runtime_id, created, modified, user_id, permission, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        permission.getId(),
                        permission.getRuntimeId(),
                        permission.getCreated().atOffset(ZoneOffset.UTC),
                        permission.getModified().atOffset(ZoneOffset.UTC),
                        permission.getUserId(),
                        permission.getPermission(),
                        permission.getDeleted()
                ),
                () -> null,
                () -> logModelFactory.create("Runtime permission was inserted, permission=" + permission)
        );
    }
}
