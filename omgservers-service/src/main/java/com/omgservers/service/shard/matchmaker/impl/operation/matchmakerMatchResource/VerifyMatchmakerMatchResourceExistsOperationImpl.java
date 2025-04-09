package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.omgservers.service.operation.server.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyMatchmakerMatchResourceExistsOperationImpl implements VerifyMatchmakerMatchResourceExistsOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long matchmakerId,
                                final Long matchId) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $shard.tab_matchmaker_match_resource
                        where matchmaker_id = $1 and match_id = $2 and deleted = false
                        limit 1
                        """,
                List.of(matchmakerId, matchId),
                "Matchmaker match resource");
    }
}
