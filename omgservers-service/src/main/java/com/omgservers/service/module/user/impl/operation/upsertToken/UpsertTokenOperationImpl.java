package com.omgservers.service.module.user.impl.operation.upsertToken;

import com.omgservers.model.token.TokenModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
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
class UpsertTokenOperationImpl implements UpsertTokenOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertToken(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final TokenModel token) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_token(id, user_id, created, expire, hash)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        token.getId(),
                        token.getUserId(),
                        token.getCreated().atOffset(ZoneOffset.UTC),
                        token.getExpire().atOffset(ZoneOffset.UTC),
                        token.getHash()),
                () -> null,
                () -> logModelFactory.create("Token was inserted, token=" + token)
        );
    }
}
