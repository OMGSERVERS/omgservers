package com.omgservers.service.module.runtime.impl.service.doService;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetObjectRequest;
import com.omgservers.model.dto.runtime.DoSetObjectResponse;
import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DoService {

    Uni<DoRespondClientResponse> doRespondClient(@Valid DoRespondClientRequest request);

    Uni<DoSetAttributesResponse> doSetAttributes(@Valid DoSetAttributesRequest request);

    Uni<DoSetObjectResponse> doSetObject(@Valid DoSetObjectRequest request);

    Uni<DoKickClientResponse> doKickClient(@Valid DoKickClientRequest request);

    Uni<DoStopRuntimeResponse> doStopRuntime(@Valid DoStopRuntimeRequest request);

    Uni<DoChangePlayerResponse> doChangePlayer(@Valid DoChangePlayerRequest request);

    Uni<DoUnicastMessageResponse> doUnicastMessage(@Valid DoUnicastMessageRequest request);

    Uni<DoMulticastMessageResponse> doMulticastMessage(@Valid DoMulticastMessageRequest request);

    Uni<DoBroadcastMessageResponse> doBroadcastMessage(@Valid DoBroadcastMessageRequest request);
}
