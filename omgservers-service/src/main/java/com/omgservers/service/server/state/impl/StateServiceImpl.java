package com.omgservers.service.server.state.impl;

import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.GetIndexConfigRequest;
import com.omgservers.service.server.state.dto.GetIndexConfigResponse;
import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetIndexConfigRequest;
import com.omgservers.service.server.state.dto.SetIndexConfigResponse;
import com.omgservers.service.server.state.dto.SetNodeIdRequest;
import com.omgservers.service.server.state.dto.SetNodeIdResponse;
import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetServiceTokenResponse;
import com.omgservers.service.server.state.impl.method.GetIndexConfigMethod;
import com.omgservers.service.server.state.impl.method.GetNodeIdMethod;
import com.omgservers.service.server.state.impl.method.GetServiceTokenMethod;
import com.omgservers.service.server.state.impl.method.SetIndexConfigMethod;
import com.omgservers.service.server.state.impl.method.SetNodeIdMethod;
import com.omgservers.service.server.state.impl.method.SetServiceTokenMethod;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StateServiceImpl implements StateService {

    final SetServiceTokenMethod setServiceTokenMethod;
    final GetServiceTokenMethod getServiceTokenMethod;
    final SetIndexConfigMethod setIndexConfigMethod;
    final GetIndexConfigMethod getIndexConfigMethod;
    final SetNodeIdMethod setNodeIdMethod;
    final GetNodeIdMethod getNodeIdMethod;

    @Override
    public SetIndexConfigResponse execute(@Valid final SetIndexConfigRequest request) {
        return setIndexConfigMethod.execute(request);
    }

    @Override
    public GetIndexConfigResponse execute(@Valid final GetIndexConfigRequest request) {
        return getIndexConfigMethod.execute(request);
    }

    @Override
    public SetNodeIdResponse execute(@Valid final SetNodeIdRequest request) {
        return setNodeIdMethod.execute(request);
    }

    @Override
    public GetNodeIdResponse execute(@Valid final GetNodeIdRequest request) {
        return getNodeIdMethod.execute(request);
    }

    @Override
    public SetServiceTokenResponse execute(@Valid final SetServiceTokenRequest request) {
        return setServiceTokenMethod.execute(request);
    }

    @Override
    public GetServiceTokenResponse execute(@Valid final GetServiceTokenRequest request) {
        return getServiceTokenMethod.execute(request);
    }
}
