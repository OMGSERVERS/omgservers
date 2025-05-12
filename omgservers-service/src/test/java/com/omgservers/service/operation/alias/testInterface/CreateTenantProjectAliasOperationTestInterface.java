package com.omgservers.service.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.operation.alias.CreateTenantAliasResult;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class CreateTenantProjectAliasOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final CreateTenantProjectAliasOperation createTenantProjectAliasOperation;

    public AliasModel execute(final Long tenantId,
                              final Long tenantProjectId,
                              final String aliasValue) {
        return createTenantProjectAliasOperation.execute(tenantId, tenantProjectId, aliasValue)
                .map(CreateTenantProjectAliasResult::alias)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
