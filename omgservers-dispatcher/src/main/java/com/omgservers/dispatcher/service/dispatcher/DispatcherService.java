package com.omgservers.dispatcher.service.dispatcher;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<CreateDispatcherResponse> execute(@Valid CreateDispatcherRequest request);

    Uni<DeleteDispatcherResponse> execute(@Valid DeleteDispatcherRequest request);

    Uni<AddPlayerConnectionResponse> execute(@Valid AddPlayerConnectionRequest request);

    Uni<RemovePlayerConnectionResponse> execute(@Valid RemovePlayerConnectionRequest request);

    Uni<TransferTextMessageResponse> execute(@Valid TransferTextMessageRequest request);

    Uni<TransferBinaryMessageResponse> execute(@Valid TransferBinaryMessageRequest request);
}
