package com.omgservers.application.module.tenantModule.model.tenant;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantModel {

    static public void validate(TenantModel tenant) {
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
    }

    Long id;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    @ToString.Exclude
    TenantConfigModel config;
}
