package com.omgservers.service.operation.assignment;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;

public interface SelectRandomMatchmakerOperation {
    Uni<MatchmakerModel> execute(Long tenantId, Long tenantDeploymentId);
}
