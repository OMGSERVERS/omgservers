package com.omgservers.application.module.userModule.impl.service.objectInternalService;

import com.omgservers.dto.userModule.DeleteObjectInternalRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.GetObjectInternalRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.SyncObjectInternalRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ObjectInternalService {

    Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request);
}
