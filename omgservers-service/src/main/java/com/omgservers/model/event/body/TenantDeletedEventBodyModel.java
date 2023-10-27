package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.tenant.TenantModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TenantDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    TenantModel tenant;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Long getGroupId() {
        return tenant.getId();
    }
}
