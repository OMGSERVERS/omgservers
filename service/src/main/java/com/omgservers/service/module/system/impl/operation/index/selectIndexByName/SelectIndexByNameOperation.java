package com.omgservers.service.module.system.impl.operation.index.selectIndexByName;

import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectIndexByNameOperation {
    Uni<IndexModel> selectIndexByName(SqlConnection sqlConnection, String name);
}
