package com.omgservers.service.server.state.impl;

import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.GetIndexConfigRequest;
import com.omgservers.service.server.state.dto.GetIndexConfigResponse;
import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
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
import com.omgservers.service.server.state.impl.method.GetIndexConfigMethod;
import com.omgservers.service.server.state.impl.method.GetNodeIdMethod;
import com.omgservers.service.server.state.impl.method.GetServiceTokenMethod;
import com.omgservers.service.server.state.impl.method.GetX5CMethod;
import com.omgservers.service.server.state.impl.method.SetIndexConfigMethod;
import com.omgservers.service.server.state.impl.method.SetNodeIdMethod;
import com.omgservers.service.server.state.impl.method.SetServiceTokenMethod;
import com.omgservers.service.server.state.impl.method.SetX5CMethod;
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
    final SetX5CMethod setX5CMethod;
    final GetX5CMethod getX5CMethod;

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

    @Override
    public SetX5CResponse execute(@Valid final SetX5CRequest request) {
        return setX5CMethod.execute(request);
    }

    @Override
    public GetX5CResponse execute(@Valid final GetX5CRequest request) {
        return getX5CMethod.execute(request);
    }
}
