package com.omgservers.service.server.state;

import com.omgservers.service.server.state.dto.GetIndexConfigRequest;
import com.omgservers.service.server.state.dto.GetIndexConfigResponse;
import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import com.omgservers.service.server.state.dto.GetX5CRequest;
import com.omgservers.service.server.state.dto.GetX5CResponse;
import com.omgservers.service.server.state.dto.SetIndexConfigRequest;
import com.omgservers.service.server.state.dto.SetIndexConfigResponse;
import com.omgservers.service.server.state.dto.SetNodeIdRequest;
import com.omgservers.service.server.state.dto.SetNodeIdResponse;
import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetServiceTokenResponse;
import com.omgservers.service.server.state.dto.SetX5CRequest;
import com.omgservers.service.server.state.dto.SetX5CResponse;
import jakarta.validation.Valid;

public interface StateService {

    SetIndexConfigResponse execute(@Valid SetIndexConfigRequest request);

    GetIndexConfigResponse execute(@Valid GetIndexConfigRequest request);

    SetNodeIdResponse execute(@Valid SetNodeIdRequest request);

    GetNodeIdResponse execute(@Valid GetNodeIdRequest request);

    SetServiceTokenResponse execute(@Valid SetServiceTokenRequest request);

    GetServiceTokenResponse execute(@Valid GetServiceTokenRequest request);

    SetX5CResponse execute(@Valid SetX5CRequest request);

    GetX5CResponse execute(@Valid GetX5CRequest request);
}
