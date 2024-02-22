package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchRuntimeRefMethod {
    Uni<DeleteMatchRuntimeRefResponse> deleteMatchRuntimeRef(DeleteMatchRuntimeRefRequest request);
}
