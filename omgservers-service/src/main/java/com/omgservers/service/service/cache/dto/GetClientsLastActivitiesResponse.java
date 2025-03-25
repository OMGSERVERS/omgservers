package com.omgservers.service.service.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientsLastActivitiesResponse {

    Map<Long, Instant> lastActivities;
}
