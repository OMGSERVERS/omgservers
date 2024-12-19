package com.omgservers.service.module.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertAliasOperationImpl implements UpsertAliasOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final AliasModel alias) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_alias(
                            id, idempotency_key, created, modified, shard_key, alias_value, entity_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        alias.getId(),
                        alias.getIdempotencyKey(),
                        alias.getCreated().atOffset(ZoneOffset.UTC),
                        alias.getModified().atOffset(ZoneOffset.UTC),
                        alias.getShardKey(),
                        alias.getValue(),
                        alias.getEntityId(),
                        alias.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
