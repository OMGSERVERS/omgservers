package com.omgservers.service.server.task.impl.method.executeStageTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.FetchStageOperation;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.HandleStageOperation;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.UpdateTenantStageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageTaskImpl implements Task<StageTaskArguments> {

    final UpdateTenantStageOperation updateTenantStageOperation;
    final HandleStageOperation handleStageOperation;
    final FetchStageOperation fetchStageOperation;

    public Uni<Boolean> execute(final StageTaskArguments taskArguments) {
        final var tenantId = taskArguments.tenantId();
        final var tenantStageId = taskArguments.tenantStageId();

        return fetchStageOperation.execute(tenantId, tenantStageId)
                .map(handleStageOperation::execute)
                .flatMap(handleTenantStageResult -> {
                    final var tenantStageChangeOfState = handleTenantStageResult.tenantStageChangeOfState();
                    if (tenantStageChangeOfState.isNotEmpty()) {
                        log.info("Update tenant stage \"{}\" state in tenant \"{}\", {}",
                                tenantStageId, tenantId, tenantStageChangeOfState);
                        return updateTenantStageOperation.execute(handleTenantStageResult);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                })
                .replaceWith(Boolean.FALSE);
    }
}
