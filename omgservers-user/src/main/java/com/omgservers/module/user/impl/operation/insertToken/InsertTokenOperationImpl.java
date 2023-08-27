package com.omgservers.module.user.impl.operation.insertToken;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.module.user.impl.operation.encodeToken.EncodeTokenOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
class InsertTokenOperationImpl implements InsertTokenOperation {

    static private final String sql = """
            insert into $schema.tab_user_token(id, user_id, created, expire, hash)
            values($1, $2, $3, $4, $5)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final EncodeTokenOperation encodeTokenOperation;
    final GenerateIdOperation generateIdOperation;
    final GetConfigOperation getConfigOperation;

    final SecureRandom secureRandom;

    public InsertTokenOperationImpl(GetConfigOperation getConfigOperation,
                                    EncodeTokenOperation encodeTokenOperation,
                                    GenerateIdOperation generateIdOperation,
                                    PrepareShardSqlOperation prepareShardSqlOperation) {
        this.getConfigOperation = getConfigOperation;
        this.encodeTokenOperation = encodeTokenOperation;
        this.prepareShardSqlOperation = prepareShardSqlOperation;
        this.generateIdOperation = generateIdOperation;
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
        final var tokenId = generateIdOperation.generateId();
        final var userId = user.getId();
        final var tokenObject = createSecurityToken(tokenId, userId, user.getRole());
        final var rawToken = encodeTokenOperation.encodeToken(tokenObject);
        final var tokenHash = BcryptUtil.bcryptHash(rawToken);
        final var result = new UserTokenContainerModel(tokenObject, rawToken, lifetime);

        TokenModel tokenModel = new TokenModel();
        tokenModel.setId(tokenId);
        tokenModel.setUserId(userId);
        tokenModel.setCreated(now);
        tokenModel.setExpire(now.plusSeconds(lifetime));
        tokenModel.setHash(tokenHash);

        return insertQuery(sqlConnection, shard, tokenModel)
                .invoke(voidItem -> log.info("Token was inserted, shard={}, token={}", shard, tokenObject))
                .replaceWith(result);

    }

    UserTokenModel createSecurityToken(Long id, Long userId, UserRoleEnum role) {
        var token = new UserTokenModel();
        token.setId(id);
        token.setUserId(userId);
        token.setRole(role);
        token.setSecret(Math.abs(secureRandom.nextLong()));
        return token;
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, TokenModel tokenModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        tokenModel.getId(),
                        tokenModel.getUserId(),
                        tokenModel.getCreated().atOffset(ZoneOffset.UTC),
                        tokenModel.getExpire().atOffset(ZoneOffset.UTC),
                        tokenModel.getHash())))
                .replaceWithVoid();
    }
}
