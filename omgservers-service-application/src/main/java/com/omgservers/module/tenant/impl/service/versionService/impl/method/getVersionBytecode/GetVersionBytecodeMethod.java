package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionBytecode;

import com.omgservers.model.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.model.dto.tenant.GetVersionBytecodeResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionBytecodeMethod {

    Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request);
}
