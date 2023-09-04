package com.omgservers.module.internal.impl.service.changeService.impl;

import com.omgservers.module.internal.impl.service.changeService.ChangeService;
import com.omgservers.module.internal.impl.service.changeService.impl.method.change.ChangeMethod;
import com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithEvent.ChangeWithEventMethod;
import com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithLog.ChangeWithLogMethod;
import com.omgservers.ChangeRequest;
import com.omgservers.ChangeWithEventRequest;
import com.omgservers.ChangeWithLogRequest;
import com.omgservers.ChangeResponse;
import com.omgservers.ChangeWithEventResponse;
import com.omgservers.ChangeWithLogResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ChangeServiceImpl implements ChangeService {

    final ChangeMethod changeMethod;
    final ChangeWithLogMethod changeWithLogMethod;
    final ChangeWithEventMethod changeWithEventMethod;

    @Override
    public Uni<ChangeResponse> change(ChangeRequest request) {
        return changeMethod.change(request);
    }

    @Override
    public Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request) {
        return changeWithLogMethod.changeWithLog(request);
    }

    @Override
    public Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request) {
        return changeWithEventMethod.changeWithEvent(request);
    }
}
