package com.omgservers.module.runtime.impl.service.runtimeShardedService;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeShardedService {

    Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request);

    Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request);

    Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request);

    Uni<SyncRuntimeCommandShardedResponse> syncCommand(SyncRuntimeCommandShardedRequest request);

    Uni<DeleteRuntimeCommandShardedResponse> deleteCommand(DeleteRuntimeCommandShardedRequest request);

    Uni<Void> doUpdate(DoUpdateShardedRequest request);
}
