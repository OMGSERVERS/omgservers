package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.getIdMethod;

import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
import io.smallrye.mutiny.Uni;

public interface GenerateIdMethod {
    Uni<GenerateIdAdminResponse> getId();
}
