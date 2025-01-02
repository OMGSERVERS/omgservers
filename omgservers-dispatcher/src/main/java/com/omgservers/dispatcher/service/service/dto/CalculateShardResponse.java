package com.omgservers.dispatcher.service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShardResponse {

    URI serverUri;
    Boolean foreign;
}
