package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.mappers.AliasModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveAliasesByShardKeyOperationImpl implements SelectActiveAliasesByShardKeyOperation {

    final SelectListOperation selectListOperation;

    final AliasModelMapper aliasModelMapper;

    @Override
    public Uni<List<AliasModel>> execute(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long shardKey) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, uniqueness_group, entity_id, alias_value, deleted
                        from $schema.tab_alias
                        where shard_key = $1 and deleted = false
                        order by id asc
                        """,
                List.of(shardKey),
                "Alias",
                aliasModelMapper::fromRow);
    }
}
