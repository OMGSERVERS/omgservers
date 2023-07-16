package com.omgservers.application.module.tenantModule.model.tenant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenantConfigModel {

    static public TenantConfigModel create() {
        TenantConfigModel config = new TenantConfigModel();
        return config;
    }
}
