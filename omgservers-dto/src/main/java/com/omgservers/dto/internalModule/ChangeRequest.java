package com.omgservers.dto.internalModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequest {

    static public void validate(ChangeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ShardRequest request;
    BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction;
}
