package com.omgservers.application.module.userModule.impl.service.objectInternalService;

import com.omgservers.dto.userModule.DeleteObjectShardRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.GetObjectShardRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.SyncObjectShardRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ObjectInternalService {

    Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request);
}
