package com.omgservers.base.impl.operation.changeOperation;

import com.omgservers.base.ShardModel;
import com.omgservers.dto.InternalRequest;
import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.log.LogModel;
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
