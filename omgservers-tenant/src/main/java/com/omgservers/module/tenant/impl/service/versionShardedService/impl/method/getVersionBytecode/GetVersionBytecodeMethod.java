package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersionBytecode;

import com.omgservers.dto.tenant.GetVersionBytecodeShardedRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionBytecodeMethod {

    Uni<GetVersionBytecodeShardedResponse> getVersionBytecode(GetVersionBytecodeShardedRequest request);
}
