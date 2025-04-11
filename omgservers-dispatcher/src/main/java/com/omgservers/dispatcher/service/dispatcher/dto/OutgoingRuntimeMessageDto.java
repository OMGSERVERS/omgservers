package com.omgservers.dispatcher.service.dispatcher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingRuntimeMessageDto {

    @NotNull
    MessageEncodingEnum encoding;

    @NotBlank
    String message;

    // If null, it is broadcasting message
    List<Long> clients;
}
