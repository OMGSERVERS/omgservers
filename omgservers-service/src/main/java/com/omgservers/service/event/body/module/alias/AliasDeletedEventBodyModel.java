package com.omgservers.service.event.body.module.alias;

import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AliasDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    String shardKey;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ALIAS_DELETED;
    }
}
