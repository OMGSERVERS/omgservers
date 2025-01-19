package com.omgservers.service.module.matchmaker.impl.operation.matchmaker;

import com.omgservers.service.operation.server.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasMatchmakerOperationImpl implements HasMatchmakerOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_matchmaker
                        where id = $1 and deleted = false
                        limit 1
                        """,
                Collections.singletonList(id),
                "Matchmaker");
    }
}
