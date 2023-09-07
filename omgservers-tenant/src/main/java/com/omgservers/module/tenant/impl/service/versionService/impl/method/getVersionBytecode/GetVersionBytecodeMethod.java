package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionBytecode;

import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionBytecodeMethod {

    Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request);
}
