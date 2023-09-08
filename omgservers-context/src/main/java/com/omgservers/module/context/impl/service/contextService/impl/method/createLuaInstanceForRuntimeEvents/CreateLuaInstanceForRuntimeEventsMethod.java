package com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForRuntimeEvents;

import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsResponse;
import io.smallrye.mutiny.Uni;

public interface CreateLuaInstanceForRuntimeEventsMethod {
    Uni<CreateLuaInstanceForRuntimeEventsResponse> createLuaInstanceForRuntimeEvents(CreateLuaInstanceForRuntimeEventsRequest request);
}
