package com.omgservers.module.user.impl.service.objectService;

import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.DeleteObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import io.smallrye.mutiny.Uni;

public interface ObjectService {

    Uni<GetObjectResponse> getObject(GetObjectRequest request);

    Uni<SyncObjectResponse> syncObject(SyncObjectRequest request);

    Uni<DeleteObjectResponse> deleteObject(DeleteObjectRequest request);
}
