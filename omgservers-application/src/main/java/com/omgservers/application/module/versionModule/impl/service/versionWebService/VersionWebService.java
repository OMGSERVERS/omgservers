package com.omgservers.application.module.versionModule.impl.service.versionWebService;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.*;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface VersionWebService {

    Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request);

    Uni<Void> createVersion(CreateVersionInternalRequest request);

    Uni<Void> syncVersion(SyncVersionInternalRequest request);

    Uni<Void> deleteVersion(DeleteVersionInternalRequest request);

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request);

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request);
}
