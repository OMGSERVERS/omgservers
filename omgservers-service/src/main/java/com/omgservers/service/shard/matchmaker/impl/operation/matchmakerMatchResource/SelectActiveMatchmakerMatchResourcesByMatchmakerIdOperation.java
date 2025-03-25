package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation {
    Uni<List<MatchmakerMatchResourceModel>> execute(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long matchmakerId);
}
