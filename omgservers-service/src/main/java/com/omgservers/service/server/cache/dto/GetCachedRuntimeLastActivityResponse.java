package com.omgservers.service.server.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCachedRuntimeLastActivityResponse {

    Instant lastActivity;
}
