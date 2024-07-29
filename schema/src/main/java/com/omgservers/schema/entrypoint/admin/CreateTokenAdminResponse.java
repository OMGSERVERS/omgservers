package com.omgservers.schema.entrypoint.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenAdminResponse {

    @ToString.Exclude
    String rawToken;
}
