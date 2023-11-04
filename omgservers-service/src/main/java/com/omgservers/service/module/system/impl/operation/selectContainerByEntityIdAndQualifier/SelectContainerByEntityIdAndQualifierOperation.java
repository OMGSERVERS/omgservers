package com.omgservers.service.module.system.impl.operation.selectContainerByEntityIdAndQualifier;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectContainerByEntityIdAndQualifierOperation {
    Uni<ContainerModel> selectContainerByEntityIdAndQualifier(SqlConnection sqlConnection,
                                                              Long entityId,
                                                              ContainerQualifierEnum qualifier,
                                                              Boolean deleted);
}
