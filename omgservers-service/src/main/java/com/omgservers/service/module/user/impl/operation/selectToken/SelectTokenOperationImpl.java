package com.omgservers.service.module.user.impl.operation.selectToken;

import com.omgservers.model.token.TokenModel;
import com.omgservers.service.module.user.impl.mapper.TokenModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTokenOperationImpl implements SelectTokenOperation {

    final SelectObjectOperation selectObjectOperation;

    final TokenModelMapper tokenModelMapper;

    @Override
    public Uni<TokenModel> selectToken(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, user_id, created, modified, expire, hash, deleted
                        from $schema.tab_user_token
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Token",
                tokenModelMapper::fromRow);
    }
}
