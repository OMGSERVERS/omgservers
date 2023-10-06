package com.omgservers.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerIdAndStatus;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchmakerCommandsByMatchmakerIdAndStatusOperation {
    Uni<List<MatchmakerCommandModel>> selectMatchmakerCommandsByMatchmakerIdAndStatus(SqlConnection sqlConnection,
                                                                                      int shard,
                                                                                      Long matchmakerId,
                                                                                      MatchmakerCommandStatusEnum status);
}
