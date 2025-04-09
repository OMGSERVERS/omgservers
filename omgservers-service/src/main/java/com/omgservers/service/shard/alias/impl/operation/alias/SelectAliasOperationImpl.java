package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.mappers.AliasModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAliasOperationImpl implements SelectAliasOperation {

    final SelectObjectOperation selectObjectOperation;

    final AliasModelMapper aliasModelMapper;

    @Override
    public Uni<AliasModel> execute(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, uniqueness_group, entity_id, alias_value, deleted
                        from $shard.tab_alias
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Alias",
                aliasModelMapper::fromRow);
    }
}
