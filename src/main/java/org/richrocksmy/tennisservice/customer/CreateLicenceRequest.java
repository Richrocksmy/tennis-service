package org.richrocksmy.tennisservice.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreateLicenceRequest {

    @NotNull
    private UUID eventId;

}
