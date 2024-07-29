package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.upsertMatchmakerRequest;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerRequestOperation {
    Uni<Boolean> upsertMatchmakerRequest(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         MatchmakerRequestModel matchmakerRequest);
}
