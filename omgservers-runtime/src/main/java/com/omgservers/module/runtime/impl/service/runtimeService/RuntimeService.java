package com.omgservers.module.runtime.impl.service.runtimeService;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<GetRuntimeResponse> getRuntime(@Valid GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncRuntime(@Valid SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(@Valid DeleteRuntimeRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid SyncRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid DeleteRuntimeCommandRequest request);

    Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(@Valid DoRuntimeUpdateRequest request);
}
