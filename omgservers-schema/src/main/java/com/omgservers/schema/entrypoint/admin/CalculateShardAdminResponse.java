package com.omgservers.schema.entrypoint.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShardAdminResponse {

    int slot;
    URI uri;
}
