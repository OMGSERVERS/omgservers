package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CredentialsMessageBodyModel extends MessageBodyModel {

    @NotNull
    Long userId;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String password;
}
