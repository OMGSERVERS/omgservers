package com.omgservers.application.module.tenantModule.model.tenant;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantModel {

    static public TenantModel create(final TenantConfigModel config) {
        return create(UUID.randomUUID(), config);
    }

    static public TenantModel create(final UUID uuid,
                                     final TenantConfigModel config) {
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        TenantModel tenant = new TenantModel();
        tenant.setCreated(now);
        tenant.setModified(now);
        tenant.setUuid(uuid);
        tenant.setConfig(config);
        return tenant;
    }

    static public void validate(TenantModel tenant) {
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
    }

    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    @ToString.Exclude
    TenantConfigModel config;
}
