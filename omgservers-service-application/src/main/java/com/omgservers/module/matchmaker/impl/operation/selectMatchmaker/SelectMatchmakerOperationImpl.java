package com.omgservers.module.matchmaker.impl.operation.selectMatchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.module.matchmaker.impl.mappers.MatchmakerMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerOperationImpl implements SelectMatchmakerOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerMapper matchmakerMapper;

    @Override
    public Uni<MatchmakerModel> selectMatchmaker(final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, tenant_id, version_id
                        from $schema.tab_matchmaker
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Matchmaker",
                matchmakerMapper::fromRow);
    }
}