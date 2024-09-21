package com.omgservers.schema.module.tenant.tenantLobbyRef;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantLobbyRefResponse {

    TenantLobbyRefModel tenantLobbyRef;
}
