package com.omgservers.service.module.player.impl.service.webService;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.HandleMessagePlayerRequest;
import com.omgservers.model.dto.player.HandleMessagePlayerResponse;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerRequest;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateUserPlayerResponse> createUser(CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> createToken(CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> createClient(CreateClientPlayerRequest request);

    Uni<HandleMessagePlayerResponse> handleMessage(HandleMessagePlayerRequest request);

    Uni<ReceiveMessagesPlayerResponse> receiveMessages(ReceiveMessagesPlayerRequest request);
}
