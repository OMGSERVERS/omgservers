package com.omgservers.service.module.runtime.impl.operation.countRuntimeClients;

import com.omgservers.service.module.runtime.impl.mapper.RuntimeClientModelMapper;
import com.omgservers.service.operation.returnCount.ReturnCountOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CountRuntimeClientsOperationImpl implements
        CountRuntimeClientsOperation {

    final ReturnCountOperation returnCountOperation;

    final RuntimeClientModelMapper runtimeClientModelMapper;

    @Override
    public Uni<Integer> countRuntimeClients(final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long runtimeId) {
        return returnCountOperation.returnCount(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_client
                        where runtime_id = $1
                        """,
                Collections.singletonList(runtimeId)
        );
    }
}
