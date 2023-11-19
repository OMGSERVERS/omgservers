package com.omgservers.model.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndexAdminRequest {

    @NotEmpty
    List<URI> addresses;
}
