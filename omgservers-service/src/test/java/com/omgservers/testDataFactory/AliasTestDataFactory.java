package com.omgservers.testDataFactory;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.testInterface.CreateTenantAliasOperationTestInterface;
import com.omgservers.service.operation.alias.testInterface.CreateTenantProjectAliasOperationTestInterface;
import com.omgservers.service.shard.alias.service.testInterface.AliasServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasTestDataFactory {

    final AliasServiceTestInterface aliasService;

    final CreateTenantProjectAliasOperationTestInterface createTenantProjectAliasOperation;
    final CreateTenantAliasOperationTestInterface createTenantAliasOperation;

    final AliasModelFactory aliasModelFactory;

    public AliasModel createAlias(final TenantModel tenant, final String aliasValue) {
        final var tenantId = tenant.getId();

        final var alias = createTenantAliasOperation.execute(tenantId, aliasValue);
        return alias;
    }

    public AliasModel createAlias(final TenantProjectModel tenantProject, final String aliasValue) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();

        final var alias = createTenantProjectAliasOperation.execute(tenantId, tenantProjectId, aliasValue);
        return alias;
    }
}
