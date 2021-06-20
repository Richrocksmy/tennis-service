package org.richrocksmy.tennisservice.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCustomerRequest {

    @NotNull
    private String companyName;

}
