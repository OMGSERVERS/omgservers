package com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithLog;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.model.log.LogModel;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
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
    final GetConfigOperation getConfigOperation;

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
                .map(this::prepareMethodResponse);
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

    ChangeWithLogResponse prepareMethodResponse(TransactionResult transactionResult) {
        final var changeWithLogResponse = new ChangeWithLogResponse();
        changeWithLogResponse.setResult(transactionResult.result);
        if (getConfigOperation.getConfig().verbose()) {
            final var extendedResponse = new ChangeWithLogResponse.ExtendedResponse();
            extendedResponse.setChangeLog(transactionResult.changeLog);
            changeWithLogResponse.setExtendedResponse(extendedResponse);
        }
        return changeWithLogResponse;
    }

    private record TransactionResult(boolean result,
                                     LogModel changeLog) {
    }
}
