package com.omgservers.service.master.entity.impl.operation;

import com.omgservers.schema.model.entity.EntityModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveEntitiesOperation {
    Uni<List<EntityModel>> execute(SqlConnection sqlConnection);
}
