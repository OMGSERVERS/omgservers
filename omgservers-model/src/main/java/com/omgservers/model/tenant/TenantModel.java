package com.omgservers.model.tenant;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantModel {

    public static void validate(TenantModel tenant) {
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    @ToString.Exclude
    TenantConfigModel config;
}
