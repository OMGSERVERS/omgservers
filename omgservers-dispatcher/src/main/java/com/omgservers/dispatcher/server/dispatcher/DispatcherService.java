package com.omgservers.dispatcher.server.dispatcher;

import com.omgservers.dispatcher.server.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageResponse;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageResponse;
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
