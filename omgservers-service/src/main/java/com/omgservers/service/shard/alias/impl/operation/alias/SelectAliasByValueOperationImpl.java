package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.alias.impl.mappers.AliasModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAliasByValueOperationImpl
        implements SelectAliasByValueOperation {

    final SelectObjectOperation selectObjectOperation;

    final AliasModelMapper aliasModelMapper;

    @Override
    public Uni<AliasModel> execute(final SqlConnection sqlConnection,
                                   final int slot,
                                   final AliasQualifierEnum qualifier,
                                   final String shardKey,
                                   final Long uniquenessGroup,
                                   final String value) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, created, modified, qualifier, shard_key, uniqueness_group, entity_id, alias_value, deleted
                        from $slot.tab_alias
                        where qualifier = $1 and shard_key = $2 and uniqueness_group = $3 and alias_value = $4
                        order by id desc
                        limit 1
                        """,
                List.of(qualifier, shardKey, uniquenessGroup, value),
                "Alias",
                aliasModelMapper::execute);
    }
}
