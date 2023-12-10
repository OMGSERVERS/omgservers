package com.omgservers.service.module.system.impl.operation.selectEntityByEntityId;

import com.omgservers.model.entitiy.EntityModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEntityByEntityIdOperation {
    Uni<EntityModel> selectEntityByEntityId(SqlConnection sqlConnection,
                                            Long entityId);
}
