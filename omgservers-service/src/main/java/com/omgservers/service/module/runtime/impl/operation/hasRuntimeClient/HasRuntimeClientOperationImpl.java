package com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient;

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
class HasRuntimeClientOperationImpl implements HasRuntimeClientOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasRuntimeClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long runtimeId,
                                         final Long userId,
                                         final Long clientId) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_client
                        where
                            runtime_id = $1 and user_id = $2 and client_id = $3 and deleted = false
                        limit 1
                        """,
                Arrays.asList(runtimeId, userId, clientId),
                "Runtime client");
    }
}
