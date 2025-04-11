package com.omgservers.dispatcher.service.dispatcher.impl;

import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferBinaryMessageResponse;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferTextMessageResponse;
import com.omgservers.dispatcher.service.dispatcher.impl.method.AddPlayerConnectionMethod;
import com.omgservers.dispatcher.service.dispatcher.impl.method.CreateDispatcherMethod;
import com.omgservers.dispatcher.service.dispatcher.impl.method.DeleteDispatcherMethod;
import com.omgservers.dispatcher.service.dispatcher.impl.method.RemovePlayerConnectionMethod;
import com.omgservers.dispatcher.service.dispatcher.impl.method.TransferBinaryMessageMethod;
import com.omgservers.dispatcher.service.dispatcher.impl.method.TransferTextMessageMethod;
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

    final RemovePlayerConnectionMethod removePlayerConnectionMethod;
    final TransferBinaryMessageMethod transferBinaryMessageMethod;
    final TransferTextMessageMethod transferTextMessageMethod;
    final AddPlayerConnectionMethod addPlayerConnectionMethod;
    final CreateDispatcherMethod createDispatcherMethod;
    final DeleteDispatcherMethod deleteDispatcherMethod;

    @Override
    public Uni<CreateDispatcherResponse> execute(@Valid final CreateDispatcherRequest request) {
        return createDispatcherMethod.execute(request);
    }

    @Override
    public Uni<DeleteDispatcherResponse> execute(@Valid final DeleteDispatcherRequest request) {
        return deleteDispatcherMethod.execute(request);
    }

    @Override
    public Uni<AddPlayerConnectionResponse> execute(@Valid final AddPlayerConnectionRequest request) {
        return addPlayerConnectionMethod.execute(request);
    }

    @Override
    public Uni<RemovePlayerConnectionResponse> execute(@Valid final RemovePlayerConnectionRequest request) {
        return removePlayerConnectionMethod.execute(request);
    }

    @Override
    public Uni<TransferTextMessageResponse> execute(@Valid final TransferTextMessageRequest request) {
        return transferTextMessageMethod.execute(request);
    }

    @Override
    public Uni<TransferBinaryMessageResponse> execute(@Valid final TransferBinaryMessageRequest request) {
        return transferBinaryMessageMethod.execute(request);
    }
}
