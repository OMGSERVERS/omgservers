package com.omgservers.service.service.task.impl.method.executeTenantTask;

import com.omgservers.service.service.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskResponse;
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
    public Uni<ExecuteTenantTaskResponse> execute(final ExecuteTenantTaskRequest request) {
        log.trace("Requested, {}", request);

        final var tenantId = request.getTenantId();

        return tenantTask.execute(tenantId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, tenant={}, {}:{}",
                            tenantId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteTenantTaskResponse::new);
    }
}
