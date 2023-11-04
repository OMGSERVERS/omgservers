package com.omgservers.service.module.system.impl.operation.selectContainerByEntityIdAndQualifier;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.service.module.system.impl.mappers.ContainerModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectContainerByEntityIdAndQualifierOperationImpl implements SelectContainerByEntityIdAndQualifierOperation {

    final SelectObjectOperation selectObjectOperation;

    final ContainerModelMapper containerModelMapper;

    @Override
    public Uni<ContainerModel> selectContainerByEntityIdAndQualifier(final SqlConnection sqlConnection,
                                                                     final Long entityId,
                                                                     final ContainerQualifierEnum qualifier,
                                                                     final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, entity_id, qualifier, image, config, deleted
                        from system.tab_container
                        where entity_id = $1 and qualifier = $2 and deleted = $3
                        limit 1
                        """,
                Arrays.asList(entityId, qualifier, deleted),
                "Container",
                containerModelMapper::fromRow);
    }
}
