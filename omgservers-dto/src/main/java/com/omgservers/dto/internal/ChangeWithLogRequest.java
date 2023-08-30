package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
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
public class ChangeWithLogRequest {

    static public void validate(ChangeWithLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ShardedRequest request;
    BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction;
    Function<Boolean, LogModel> logFunction;
}