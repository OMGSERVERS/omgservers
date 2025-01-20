package com.omgservers.service.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.operation.alias.CreateTenantAliasOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class CreateTenantAliasOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final CreateTenantAliasOperation createTenantAliasOperation;

    public AliasModel execute(final Long tenantId, final String aliasValue) {
        return createTenantAliasOperation.execute(tenantId, aliasValue)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
