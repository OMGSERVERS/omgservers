package com.omgservers.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeveloperAdminResponse {

    Long userId;
    @ToString.Exclude
    String password;
}
