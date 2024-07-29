package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeTenantTask;

import com.omgservers.schema.service.system.task.ExecuteTenantTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteTenantTaskMethodImpl implements ExecuteTenantTaskMethod {

    final TenantTaskImpl tenantTask;

    @Override
    public Uni<ExecuteTenantTaskResponse> executeTenantTask(final ExecuteTenantTaskRequest request) {
        log.debug("Execute tenant task, request={}", request);

        final var tenantId = request.getTenantId();

        return tenantTask.executeTask(tenantId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, tenant={}, {}:{}",
                            tenantId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteTenantTaskResponse::new);
    }
}
