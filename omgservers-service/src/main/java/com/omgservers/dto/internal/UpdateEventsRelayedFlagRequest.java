package com.omgservers.dto.internal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventsRelayedFlagRequest {

    @NotNull
    @NotEmpty
    List<Long> ids;

    @NotNull
    Boolean relayed;
}
