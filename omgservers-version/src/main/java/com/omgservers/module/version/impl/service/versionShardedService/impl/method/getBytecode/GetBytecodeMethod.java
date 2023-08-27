package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getBytecode;

import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetBytecodeMethod {

    Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request);
}
