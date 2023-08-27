package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.log.LogModel;
import com.omgservers.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeWithEventRequest {

    static public void validate(ChangeWithEventRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ShardedRequest request;
    BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction;
    Function<Boolean, LogModel> logFunction;
    Function<Boolean, EventBodyModel> eventBodyFunction;
}
