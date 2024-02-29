package com.omgservers.service.module.runtime.impl.service.doService;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoStopMatchmakingRequest;
import com.omgservers.model.dto.runtime.DoStopMatchmakingResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DoService {

    Uni<DoRespondClientResponse> doRespondClient(@Valid DoRespondClientRequest request);

    Uni<DoSetAttributesResponse> doSetAttributes(@Valid DoSetAttributesRequest request);

    Uni<DoSetProfileResponse> doSetProfile(@Valid DoSetProfileRequest request);

    Uni<DoKickClientResponse> doKickClient(@Valid DoKickClientRequest request);

    Uni<DoStopMatchmakingResponse> doStopRuntime(@Valid DoStopMatchmakingRequest request);

    Uni<DoMulticastMessageResponse> doMulticastMessage(@Valid DoMulticastMessageRequest request);

    Uni<DoBroadcastMessageResponse> doBroadcastMessage(@Valid DoBroadcastMessageRequest request);
}
