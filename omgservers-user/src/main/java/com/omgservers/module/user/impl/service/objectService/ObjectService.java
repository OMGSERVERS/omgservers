package com.omgservers.module.user.impl.service.objectService;

import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.DeleteObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ObjectService {

    Uni<GetObjectResponse> getObject(@Valid GetObjectRequest request);

    Uni<SyncObjectResponse> syncObject(@Valid SyncObjectRequest request);

    Uni<DeleteObjectResponse> deleteObject(@Valid DeleteObjectRequest request);
}
