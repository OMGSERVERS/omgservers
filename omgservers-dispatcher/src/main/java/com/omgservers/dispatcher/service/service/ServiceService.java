package com.omgservers.dispatcher.service.service;

import com.omgservers.dispatcher.service.service.dto.CalculateShardRequest;
import com.omgservers.dispatcher.service.service.dto.CalculateShardResponse;
import com.omgservers.dispatcher.service.service.dto.CreateTokenRequest;
import com.omgservers.dispatcher.service.service.dto.CreateTokenResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServiceService {

    Uni<CreateTokenResponse> execute(@Valid CreateTokenRequest request);

    Uni<CalculateShardResponse> execute(@Valid CalculateShardRequest request);
}
