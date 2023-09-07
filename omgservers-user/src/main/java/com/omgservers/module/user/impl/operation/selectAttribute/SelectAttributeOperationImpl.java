package com.omgservers.module.user.impl.operation.selectAttribute;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.module.user.impl.mapper.AttributeModelMapper;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAttributeOperationImpl implements SelectAttributeOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final AttributeModelMapper attributeModelMapper;

    @Override
    public Uni<AttributeModel> selectAttribute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long userId,
                                               final Long playerId,
                                               final String name) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, player_id, created, modified, attribute_name, attribute_value
                        from $schema.tab_user_attribute
                        where user_id = $1 and player_id = $2 and attribute_name = $3
                        limit 1
                        """,
                Arrays.asList(userId, playerId, name),
                "Attribute",
                attributeModelMapper::fromRow);
    }
}
