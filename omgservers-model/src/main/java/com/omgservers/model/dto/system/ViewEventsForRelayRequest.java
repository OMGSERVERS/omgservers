package com.omgservers.model.dto.system;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewEventsForRelayRequest {

    @NotNull
    @Positive
    Integer limit;
}
