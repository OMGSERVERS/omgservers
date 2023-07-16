package com.omgservers.application.module.userModule.impl.operation.insertTokenOperation;

import com.omgservers.application.module.userModule.model.token.TokenModel;
import com.omgservers.application.module.userModule.model.user.*;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.userModule.impl.operation.encodeTokenOperation.EncodeTokenOperation;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@ApplicationScoped
class InsertTokenOperationImpl implements InsertTokenOperation {

    static private final String sql = """
            insert into $schema.tab_user_token(user_uuid, created, uuid, expire, hash)
            values($1, $2, $3, $4, $5)
            """;

    final GetConfigOperation getConfigOperation;
    final EncodeTokenOperation encodeTokenOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final SecureRandom secureRandom;

    public InsertTokenOperationImpl(GetConfigOperation getConfigOperation,
                                    EncodeTokenOperation encodeTokenOperation,
                                    PrepareShardSqlOperation prepareShardSqlOperation) {
        this.getConfigOperation = getConfigOperation;
        this.encodeTokenOperation = encodeTokenOperation;
        this.prepareShardSqlOperation = prepareShardSqlOperation;
        this.secureRandom = new SecureRandom();
    }

    @Override
    public Uni<UserTokenContainerModel> insertToken(final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final UserModel user) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }

        final var lifetime = getConfigOperation.getConfig().tokenLifetime();

        final var now = Instant.now();
        final var tokenUuid = UUID.randomUUID();
        final var userUuid = user.getUuid();
        final var tokenObject = createSecurityToken(tokenUuid, userUuid, user.getRole());
        final var rawToken = encodeTokenOperation.encodeToken(tokenObject);
        final var tokenHash = BcryptUtil.bcryptHash(rawToken);
        final var result = new UserTokenContainerModel(tokenObject, rawToken, lifetime);

        TokenModel tokenModel = new TokenModel();
        tokenModel.setUuid(tokenUuid);
        tokenModel.setUser(userUuid);
        tokenModel.setCreated(now);
        tokenModel.setExpire(now.plusSeconds(lifetime));
        tokenModel.setHash(tokenHash);

        return insertQuery(sqlConnection, shard, tokenModel)
                .invoke(voidItem -> log.info("Token was inserted, shard={}, token={}", shard, tokenObject))
                .replaceWith(result);

    }

    UserTokenModel createSecurityToken(UUID uuid, UUID user, UserRoleEnum role) {
        var token = new UserTokenModel();
        token.setUuid(uuid);
        token.setUser(user);
        token.setRole(role);
        token.setSecret(Math.abs(secureRandom.nextLong()));
        return token;
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, TokenModel tokenModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        tokenModel.getUser(),
                        tokenModel.getCreated().atOffset(ZoneOffset.UTC),
                        tokenModel.getUuid(),
                        tokenModel.getExpire().atOffset(ZoneOffset.UTC),
                        tokenModel.getHash())))
                .replaceWithVoid();
    }
}
