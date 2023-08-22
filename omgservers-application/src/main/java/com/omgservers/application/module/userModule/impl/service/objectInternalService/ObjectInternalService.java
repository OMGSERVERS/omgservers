package com.omgservers.application.module.userModule.impl.service.objectInternalService;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.DeleteObjectInternalResponse;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ObjectInternalService {

    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request);
}
