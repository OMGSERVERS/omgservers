package com.omgservers.service.component;

import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
public class ServiceTokenFactory {

    final AtomicReference<String> serviceJwtToken;

    public ServiceTokenFactory(final GetConfigOperation getConfigOperation,
                               final IssueJwtTokenOperation issueJwtTokenOperation) {
        serviceJwtToken = new AtomicReference<>(issueJwtTokenOperation.issueServiceJwtToken());
        log.info("Service JWT token was issued");
    }

    //TODO: refresh token every now and again

    public String getServiceJwtToken() {
        return serviceJwtToken.get();
    }
}