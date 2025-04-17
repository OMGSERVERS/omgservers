package com.omgservers.service.server.cache.dto;

import com.omgservers.schema.model.index.IndexConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCachedIndexConfigResponse {

    IndexConfigDto indexConfig;
}
