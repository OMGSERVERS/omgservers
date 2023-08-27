package com.omgservers.module.runtime.impl.service.runtimeShardedService;

import com.omgservers.dto.runtime.DeleteCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteCommandInternalResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeInternalResponse;
import com.omgservers.dto.runtime.SyncCommandShardedRequest;
import com.omgservers.dto.runtime.SyncCommandInternalResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeShardedService {

    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardedRequest request);

    Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardedRequest request);

    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardedRequest request);

    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardedRequest request);

    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardedRequest request);

    Uni<Void> doUpdate(DoUpdateShardedRequest request);
}
