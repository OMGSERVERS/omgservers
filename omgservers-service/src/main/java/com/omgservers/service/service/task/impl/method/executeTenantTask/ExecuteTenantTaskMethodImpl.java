package com.omgservers.service.service.task.impl.method.executeTenantTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
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

    final ExecuteTaskOperation executeTaskOperation;

    final TenantTaskImpl tenantTask;

    @Override
    public Uni<ExecuteTenantTaskResponse> execute(final ExecuteTenantTaskRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();

        return executeTaskOperation.execute(tenantTask.execute(tenantId))
                .map(ExecuteTenantTaskResponse::new);
    }
}
