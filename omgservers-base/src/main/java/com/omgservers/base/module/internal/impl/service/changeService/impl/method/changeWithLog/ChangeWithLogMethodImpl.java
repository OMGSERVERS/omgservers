package com.omgservers.base.module.internal.impl.service.changeService.impl.method.changeWithLog;

import com.omgservers.base.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.base.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeWithLogMethodImpl implements ChangeWithLogMethod {

    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final PgPool pgPool;

    @Override
    public Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request) {
        ChangeWithLogRequest.validate(request);

        final var internalRequest = request.getRequest();
        final var changeFunction = request.getChangeFunction();
        final var logFunction = request.getLogFunction();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(internalRequest.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        changeFunction.apply(sqlConnection, shardModel)
                                .flatMap(result -> upsertLog(sqlConnection, result, logFunction)
                                        .map(changeLog -> new TransactionResult(result, changeLog))))
                )
                .map(transactionResult -> transactionResult.result)
                .map(ChangeWithLogResponse::new);
    }

    Uni<LogModel> upsertLog(final SqlConnection sqlConnection,
                            final boolean result,
                            final Function<Boolean, LogModel> logFunction) {
        final var changeLog = logFunction.apply(result);
        if (changeLog != null) {
            return upsertLogOperation.upsertLog(sqlConnection, changeLog)
                    .replaceWith(changeLog);
        } else {
            return Uni.createFrom().nullItem();
        }
    }

    private record TransactionResult(boolean result,
                                     LogModel changeLog) {
    }
}
