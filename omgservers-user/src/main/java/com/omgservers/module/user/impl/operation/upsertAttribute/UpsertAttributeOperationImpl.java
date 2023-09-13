package com.omgservers.module.user.impl.operation.upsertAttribute;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertAttributeOperationImpl implements UpsertAttributeOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final AttributeModel attribute) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_attribute(id, user_id, player_id, created, modified, name, attribute_value)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        attribute.getId(),
                        attribute.getUserId(),
                        attribute.getPlayerId(),
                        attribute.getCreated().atOffset(ZoneOffset.UTC),
                        attribute.getModified().atOffset(ZoneOffset.UTC),
                        attribute.getName(),
                        attribute.getValue()
                ),
                () -> null,
                () -> logModelFactory.create("Attribute was inserted, attribute=" + attribute)
        );
    }
}
