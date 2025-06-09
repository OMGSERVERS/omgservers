package com.omgservers.schema.shard.tenant.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantStageCommandResponse {

    List<TenantStageCommandModel> tenantStageCommands;
}