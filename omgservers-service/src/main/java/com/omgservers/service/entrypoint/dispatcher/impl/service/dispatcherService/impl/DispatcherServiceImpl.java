package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl;

import com.omgservers.service.entrypoint.dispatcher.dto.OnBinaryMessageDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnCloseDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnErrorDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnTextMessageDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.OnBinaryMessageMethod;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.OnCloseMethod;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.OnErrorMethod;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.OnOpenMethod;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.OnTextMessageMethod;
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
    public Uni<Void> onOpen(@Valid final OnOpenDispatcherRequest request) {
        return onOpenMethod.execute(request);
    }

    @Override
    public Uni<Void> onClose(OnCloseDispatcherRequest request) {
        return onCloseMethod.execute(request);
    }

    @Override
    public Uni<Void> onError(OnErrorDispatcherRequest request) {
        return onErrorMethod.execute(request);
    }

    @Override
    public Uni<Void> onTextMessage(OnTextMessageDispatcherRequest request) {
        return onTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(OnBinaryMessageDispatcherRequest request) {
        return onBinaryMessageMethod.execute(request);
    }
}
