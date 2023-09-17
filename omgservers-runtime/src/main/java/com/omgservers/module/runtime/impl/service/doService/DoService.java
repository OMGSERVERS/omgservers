package com.omgservers.module.runtime.impl.service.doService;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DoService {

    Uni<DoKickClientResponse> doKickClient(@Valid DoKickClientRequest request);

    Uni<DoStopRuntimeResponse> doStopRuntime(@Valid DoStopRuntimeRequest request);

    Uni<DoUnicastMessageResponse> doUnicastMessage(@Valid DoUnicastMessageRequest request);

    Uni<DoMulticastMessageResponse> doMulticastMessage(@Valid DoMulticastMessageRequest request);

    Uni<DoBroadcastMessageResponse> doBroadcastMessage(@Valid DoBroadcastMessageRequest request);
}
