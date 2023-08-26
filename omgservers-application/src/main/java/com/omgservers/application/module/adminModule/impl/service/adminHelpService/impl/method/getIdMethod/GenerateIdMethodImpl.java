package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.getIdMethod;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
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
