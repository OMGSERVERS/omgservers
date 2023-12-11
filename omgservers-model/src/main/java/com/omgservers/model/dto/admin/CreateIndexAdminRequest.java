package com.omgservers.model.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndexAdminRequest {

    @NotBlank
    String name;

    @NotNull
    Integer shards;

    @NotEmpty
    List<URI> addresses;
}
