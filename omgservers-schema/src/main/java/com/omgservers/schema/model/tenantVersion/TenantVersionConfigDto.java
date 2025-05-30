package com.omgservers.schema.model.tenantVersion;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionConfigDto {

    @Valid
    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    TenantVersionConfigVersionEnum version = TenantVersionConfigVersionEnum.V1;

    @Valid
    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    List<TenantVersionModeDto> modes = new ArrayList<>();

    Object userData;
}
