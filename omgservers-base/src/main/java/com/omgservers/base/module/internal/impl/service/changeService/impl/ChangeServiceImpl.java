package com.omgservers.base.module.internal.impl.service.changeService.impl;

import com.omgservers.base.module.internal.impl.service.changeService.ChangeService;
import com.omgservers.base.module.internal.impl.service.changeService.impl.method.change.ChangeMethod;
import com.omgservers.base.module.internal.impl.service.changeService.impl.method.changeWithEvent.ChangeWithEventMethod;
import com.omgservers.base.module.internal.impl.service.changeService.impl.method.changeWithLog.ChangeWithLogMethod;
import com.omgservers.dto.internalModule.ChangeRequest;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeResponse;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
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
