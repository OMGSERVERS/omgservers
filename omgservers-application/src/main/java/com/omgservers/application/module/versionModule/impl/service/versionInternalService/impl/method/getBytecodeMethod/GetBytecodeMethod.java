package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetBytecodeInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetBytecodeMethod {

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request);
}
