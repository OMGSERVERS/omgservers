package com.omgservers.model.dto.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeveloperSupportResponse {

    Long userId;
    @ToString.Exclude
    String password;
}
