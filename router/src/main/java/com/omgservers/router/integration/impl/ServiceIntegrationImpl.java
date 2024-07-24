package com.omgservers.router.integration.impl;

import com.omgservers.router.integration.ServiceIntegration;
import com.omgservers.router.integration.impl.service.ServiceService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ServiceIntegrationImpl implements ServiceIntegration {

    final ServiceService serviceService;
}
