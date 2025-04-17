package com.omgservers.service.server.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCachedClientsLastActivitiesResponse {

    Map<Long, Instant> lastActivities;
}
