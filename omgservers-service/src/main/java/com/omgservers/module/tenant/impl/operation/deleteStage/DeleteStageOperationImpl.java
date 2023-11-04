package com.omgservers.module.tenant.impl.operation.deleteStage;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.selectStage.SelectStageOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageOperationImpl implements DeleteStageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectStageOperation selectStageOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteStage(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final Long id) {
        return selectStageOperation.selectStage(sqlConnection, shard, tenantId, id)
                .flatMap(stage -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_tenant_stage
                                where tenant_id = $1 and id = $2
                                """,
                        Arrays.asList(tenantId, id),
                        () -> new StageDeletedEventBodyModel(stage),
                        () -> logModelFactory.create("Stage was deleted, stage=" + stage)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
