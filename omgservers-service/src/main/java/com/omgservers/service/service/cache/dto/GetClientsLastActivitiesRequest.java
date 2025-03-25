package com.omgservers.service.service.cache.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientsLastActivitiesRequest {

    @NotNull
    List<Long> clientIds;
}
