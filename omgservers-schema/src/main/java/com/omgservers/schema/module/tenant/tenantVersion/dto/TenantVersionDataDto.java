package com.omgservers.schema.module.tenant.tenantVersion.dto;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionDataDto {

    @NotNull
    TenantVersionModel tenantVersion;

    @NotNull
    List<TenantImageRefModel> tenantImageRefs;

    @NotNull
    List<TenantLobbyRefModel> tenantLobbyRefs;

    @NotNull
    List<TenantMatchmakerRefModel> tenantMatchmakerRefs;
}
