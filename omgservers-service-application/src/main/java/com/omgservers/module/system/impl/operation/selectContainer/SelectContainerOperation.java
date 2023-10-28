package com.omgservers.module.system.impl.operation.selectContainer;

import com.omgservers.model.container.ContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectContainerOperation {
    Uni<ContainerModel> selectContainer(SqlConnection sqlConnection, Long id);
}
