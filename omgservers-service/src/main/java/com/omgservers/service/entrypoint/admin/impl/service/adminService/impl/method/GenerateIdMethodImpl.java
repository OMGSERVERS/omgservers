package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.GenerateIdAdminRequest;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminResponse;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GenerateIdMethodImpl implements GenerateIdMethod {

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<GenerateIdAdminResponse> execute(final GenerateIdAdminRequest request) {
        log.trace("{}", request);
        return Uni.createFrom().item(new GenerateIdAdminResponse(generateIdOperation.generateId()));
    }
}
