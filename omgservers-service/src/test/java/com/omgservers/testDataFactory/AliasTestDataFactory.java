package com.omgservers.testDataFactory;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.service.testInterface.AliasServiceTestInterface;
import com.omgservers.service.operation.alias.testInterface.CreateTenantAliasOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasTestDataFactory {

    final AliasServiceTestInterface aliasService;

    final CreateTenantAliasOperationTestInterface createTenantAliasOperation;

    final AliasModelFactory aliasModelFactory;

    public AliasModel createAlias(final TenantModel tenant, final String aliasValue) {
        final var tenantId = tenant.getId();

        final var alias = createTenantAliasOperation.execute(tenantId, aliasValue);
        return alias;
    }
}
