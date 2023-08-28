package com.omgservers.module.runtime.impl.service.runtimeWebService;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeWebService {
    Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request);

    Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request);

    Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request);

    Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request);

    Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request);

    Uni<Void> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request);
}
