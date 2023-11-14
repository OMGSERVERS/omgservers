package com.omgservers.service.module.matchmaker.impl.operation.hasMatch;

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
class HasMatchOperationImpl implements HasMatchOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasMatch(final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long matchmakerId,
                                 final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        limit 1
                        """,
                Arrays.asList(matchmakerId, id),
                "Match");
    }
}
