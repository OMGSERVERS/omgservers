package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl;

import com.omgservers.dispatcher.entrypoint.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.dto.OnCloseEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.dto.OnErrorEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.dto.OnOpenEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.dto.OnTextMessageEntrypointRequest;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.EntrypointService;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method.OnBinaryMessageMethod;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method.OnCloseMethod;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method.OnErrorMethod;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method.OnOpenMethod;
import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method.OnTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EntrypointServiceImpl implements EntrypointService {

    final OnBinaryMessageMethod onBinaryMessageMethod;
    final OnTextMessageMethod onTextMessageMethod;
    final OnCloseMethod onCloseMethod;
    final OnErrorMethod onErrorMethod;
    final OnOpenMethod onOpenMethod;

    @Override
    public Uni<Void> onOpen(@Valid final OnOpenEntrypointRequest request) {
        return onOpenMethod.execute(request);
    }

    @Override
    public Uni<Void> onClose(@Valid final OnCloseEntrypointRequest request) {
        return onCloseMethod.execute(request);
    }

    @Override
    public Uni<Void> onError(@Valid final OnErrorEntrypointRequest request) {
        return onErrorMethod.execute(request);
    }

    @Override
    public Uni<Void> onTextMessage(@Valid final OnTextMessageEntrypointRequest request) {
        return onTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(@Valid final OnBinaryMessageEntrypointRequest request) {
        return onBinaryMessageMethod.execute(request);
    }
}
