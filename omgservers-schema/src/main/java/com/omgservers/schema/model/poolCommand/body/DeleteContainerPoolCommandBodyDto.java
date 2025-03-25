package com.omgservers.schema.model.poolCommand.body;

import com.omgservers.schema.model.poolCommand.PoolCommandBodyDto;
import com.omgservers.schema.model.poolCommand.PoolCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeleteContainerPoolCommandBodyDto extends PoolCommandBodyDto {

    @NotNull
    Long runtimeId;

    @Override
    public PoolCommandQualifierEnum getQualifier() {
        return PoolCommandQualifierEnum.DELETE_CONTAINER;
    }
}
