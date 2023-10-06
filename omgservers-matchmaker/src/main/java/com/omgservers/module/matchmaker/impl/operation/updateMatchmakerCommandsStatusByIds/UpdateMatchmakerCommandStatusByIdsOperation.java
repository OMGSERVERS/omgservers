package com.omgservers.module.matchmaker.impl.operation.updateMatchmakerCommandsStatusByIds;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface UpdateMatchmakerCommandStatusByIdsOperation {
    Uni<Boolean> updateMatchmakerCommandStatusByIds(ChangeContext<?> changeContext,
                                                    SqlConnection sqlConnection,
                                                    int shard,
                                                    Long matchmakerId,
                                                    List<Long> ids,
                                                    MatchmakerCommandStatusEnum status);
}
