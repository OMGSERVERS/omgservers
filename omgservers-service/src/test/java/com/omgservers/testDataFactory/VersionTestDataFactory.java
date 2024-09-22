package com.omgservers.testDataFactory;

import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionTestDataFactory {

    final TenantVersionModelFactory tenantVersionModelFactory;

}
