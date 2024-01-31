package com.omgservers.tester.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestClientModel {

    Long id;
    Long userId;
    String password;
    String rawToken;
    Long clientId;
}
