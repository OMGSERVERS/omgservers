package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         MatchmakerRequestModel matchmakerRequest);
}
