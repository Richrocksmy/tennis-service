package org.richrocksmy.tennisservice.customer;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;
import java.util.UUID;

import static org.richrocksmy.tennisservice.common.ResponseBuilder.createdResponse;

@Path("/v1/customers")
@Tag(name = "Customer Resource")
public class CustomerResource {

    private CustomerService customerService;

    public CustomerResource(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Operation(summary = "Create a new customer")
    public Response createCustomer(@Context final UriInfo uriInfo, final CreateCustomerRequest createCustomerRequest) {
        return createdResponse(uriInfo, customerService.createCustomer(createCustomerRequest));
    }

    @GET
    @Path("/{customerId}/matches")
    @Operation(summary = "Retrieve all tennis matches licensed for particular customerId")
    public Set<MatchResponse> retrieveAllMatchesForCustomer(@PathParam final UUID customerId) {
        return customerService.retrieveAllMatchesForCustomer(customerId);
    }

    @PATCH
    @Path("/{customerId}/licence/match")
    @Operation(summary = "Create a new tennis match licence for a particular customerId")
    public Response createLicenceForMatch(@Context final UriInfo uriInfo, @PathParam final UUID customerId,
                                          final CreateLicenceRequest createLicenceRequest) {
        customerService.createLicenceForMatch(customerId, createLicenceRequest);
        return createdResponse(uriInfo);
    }

    @PATCH
    @Path("/{customerId}/licence/tournament")
    @Operation(summary = "Create a new tennis tournament licence for a particular customerId")
    public Response createLicenceForTournament(@Context final UriInfo uriInfo, @PathParam final UUID customerId,
                                               final CreateLicenceRequest createLicenceRequest) {
        customerService.createLicenceForTournament(customerId, createLicenceRequest);
        return createdResponse(uriInfo);
    }

}