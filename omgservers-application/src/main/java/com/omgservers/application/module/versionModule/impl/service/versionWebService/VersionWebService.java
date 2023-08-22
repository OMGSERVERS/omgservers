package com.omgservers.application.module.versionModule.impl.service.versionWebService;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.DeleteVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetBytecodeInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetStageConfigInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.SyncVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.DeleteVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface VersionWebService {

    Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request);

    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request);

    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request);

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request);

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request);
}
