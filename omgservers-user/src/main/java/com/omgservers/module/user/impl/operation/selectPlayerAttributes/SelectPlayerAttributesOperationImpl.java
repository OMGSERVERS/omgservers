package com.omgservers.module.user.impl.operation.selectPlayerAttributes;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.module.user.impl.mapper.AttributeModelMapper;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerAttributesOperationImpl implements SelectPlayerAttributesOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final AttributeModelMapper attributeModelMapper;

    @Override
    public Uni<List<AttributeModel>> selectPlayerAttributes(final SqlConnection sqlConnection,
                                                            final int shard,
                                                            final Long userId,
                                                            final Long playerId) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                shard,
                """
                        select id, user_id, player_id, created, modified, attribute_name, attribute_value
                        from $schema.tab_user_attribute
                        where user_id = $1 and player_id = $2
                        """,
                Arrays.asList(userId, playerId),
                "Attribute",
                attributeModelMapper::fromRow);
    }
}
