package com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForPlayerEvents;

import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsResponse;
import io.smallrye.mutiny.Uni;

public interface CreateLuaInstanceForPlayerEventsMethod {
    Uni<CreateLuaInstanceForPlayerEventsResponse> createLuaInstanceForPlayerEvents(CreateLuaInstanceForPlayerEventsRequest request);
}
