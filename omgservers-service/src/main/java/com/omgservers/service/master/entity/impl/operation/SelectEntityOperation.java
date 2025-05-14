package com.omgservers.service.master.entity.impl.operation;

import com.omgservers.schema.model.entity.EntityModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEntityOperation {
    Uni<EntityModel> execute(SqlConnection sqlConnection,
                             Long id);
}
