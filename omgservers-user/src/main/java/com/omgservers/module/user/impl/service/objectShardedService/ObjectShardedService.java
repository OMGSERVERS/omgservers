package com.omgservers.module.user.impl.service.objectShardedService;

import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import io.smallrye.mutiny.Uni;

public interface ObjectShardedService {

    Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request);

    Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request);

    Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request);
}
