package com.omgservers.application.module.userModule.impl.service.objectInternalService;

import com.omgservers.dto.userModule.DeleteObjectRoutedRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.GetObjectRoutedRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.SyncObjectRoutedRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ObjectInternalService {

    Uni<GetObjectInternalResponse> getObject(GetObjectRoutedRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectRoutedRequest request);
}
