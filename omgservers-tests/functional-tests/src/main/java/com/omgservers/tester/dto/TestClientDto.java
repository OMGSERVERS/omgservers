package com.omgservers.tester.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestClientDto {

    Long id;
    Long userId;
    String password;
    String rawToken;
    Long clientId;
}
