package com.omgservers.service.module.matchmaker.impl.operation.matchmaker.upsertMatchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerOperation {
    Uni<Boolean> upsertMatchmaker(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  MatchmakerModel matchmaker);
}
