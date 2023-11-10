package com.omgservers.service.module.system.impl.operation.selectContainer;

import com.omgservers.model.container.ContainerModel;
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
class SelectContainerOperationImpl implements SelectContainerOperation {

    final SelectObjectOperation selectObjectOperation;

    final ContainerModelMapper containerModelMapper;

    @Override
    public Uni<ContainerModel> selectContainer(final SqlConnection sqlConnection,
                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, entity_id, qualifier, image, config, deleted
                        from system.tab_container
                        where id = $1
                        limit 1
                        """,
                Arrays.asList(id),
                "Container",
                containerModelMapper::fromRow);
    }
}
