package com.omgservers.application.operation.changeOperation;

import com.omgservers.application.InternalRequest;
import com.omgservers.application.ShardModel;
import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ChangeOperation {
    Uni<Boolean> change(InternalRequest request,
                        BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction);

    Uni<Boolean> changeWithLog(InternalRequest request,
                               BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction,
                               Function<Boolean, LogModel> logFunction);

    Uni<Boolean> changeWithEvent(InternalRequest request,
                                 BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction,
                                 Function<Boolean, LogModel> logFunction,
                                 Function<Boolean, EventBodyModel> eventBodyFunction);
}
