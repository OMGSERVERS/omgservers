package com.omgservers.service.server.cache.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCachedClientsLastActivitiesRequest {

    @NotNull
    List<Long> clientIds;
}
