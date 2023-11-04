package com.omgservers.module.admin.impl.service.adminService.impl.method.generateId;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
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
    public Uni<GenerateIdAdminResponse> getId() {
        return Uni.createFrom().item(new GenerateIdAdminResponse(generateIdOperation.generateId()));
    }
}
