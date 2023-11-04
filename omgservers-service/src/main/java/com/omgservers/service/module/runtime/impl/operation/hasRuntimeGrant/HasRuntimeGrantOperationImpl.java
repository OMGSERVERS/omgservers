package com.omgservers.service.module.runtime.impl.operation.hasRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRuntimeGrantOperationImpl implements HasRuntimeGrantOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasRuntimeGrant(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long runtimeId,
                                        final Long shardKey,
                                        final Long entityId,
                                        final RuntimeGrantTypeEnum type) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and shard_key = $2 and entity_id = $3 and type = $4
                        limit 1
                        """,
                Arrays.asList(runtimeId, shardKey, entityId, type),
                "Runtime grant");
    }
}
