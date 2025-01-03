package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl;

import com.omgservers.dispatcher.module.impl.dto.OnBinaryMessageRequest;
import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
import com.omgservers.dispatcher.module.impl.dto.OnErrorRequest;
import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.DispatcherService;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method.OnBinaryMessageMethod;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method.OnCloseMethod;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method.OnErrorMethod;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method.OnOpenMethod;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method.OnTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherServiceImpl implements DispatcherService {

    final OnBinaryMessageMethod onBinaryMessageMethod;
    final OnTextMessageMethod onTextMessageMethod;
    final OnCloseMethod onCloseMethod;
    final OnErrorMethod onErrorMethod;
    final OnOpenMethod onOpenMethod;

    @Override
    public Uni<Void> execute(@Valid final OnOpenRequest request) {
        return onOpenMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final OnCloseRequest request) {
        return onCloseMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final OnErrorRequest request) {
        return onErrorMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final OnTextMessageRequest request) {
        return onTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final OnBinaryMessageRequest request) {
        return onBinaryMessageMethod.execute(request);
    }
}
