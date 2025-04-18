package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchResourceOperation {
    Uni<MatchmakerMatchResourceModel> execute(SqlConnection sqlConnection,
                                              int slot,
                                              Long matchmakerId,
                                              Long id);
}
