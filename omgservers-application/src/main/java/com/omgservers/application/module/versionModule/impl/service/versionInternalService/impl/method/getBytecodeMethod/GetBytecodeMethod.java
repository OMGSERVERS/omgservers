package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod;

import com.omgservers.dto.versionModule.GetBytecodeShardRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetBytecodeMethod {

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeShardRequest request);
}
