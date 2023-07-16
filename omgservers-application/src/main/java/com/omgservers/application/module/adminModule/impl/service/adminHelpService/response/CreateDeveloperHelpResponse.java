package com.omgservers.application.module.adminModule.impl.service.adminHelpService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeveloperHelpResponse {

    UUID user;
    @ToString.Exclude
    String password;
}
