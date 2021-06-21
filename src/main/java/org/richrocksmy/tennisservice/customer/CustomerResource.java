package org.richrocksmy.tennisservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.richrocksmy.tennisservice.customer.matchsummary.SummaryType;
import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;
import java.util.UUID;

import static org.richrocksmy.tennisservice.common.ResponseBuilder.createdResponse;

@Slf4j
@Path("/v1/customers")
@Tag(name = "Customer Resource")
public class CustomerResource {

    private CustomerService customerService;

    public CustomerResource(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Operation(summary = "Create a new customer")
    public Response createCustomer(@Context final UriInfo uriInfo, @Valid final CreateCustomerRequest createCustomerRequest) {
        log.debug("Received request to create customer");
        return createdResponse(uriInfo, customerService.createCustomer(createCustomerRequest));
    }

    @GET
    @Path("/{customerId}/matches")
    @Operation(summary = "Retrieve all tennis matches licensed for this customerId")
    public Set<MatchResponse> retrieveAllMatchesForCustomer(@PathParam final UUID customerId,
                                                            @QueryParam("summaryType") final SummaryType summaryType) {
        log.debug("Received request to retrieve all matches for customer - {}", customerId);
        return customerService.retrieveAllMatchesForCustomer(customerId, summaryType);
    }

    @PATCH
    @Path("/{customerId}/licence/match")
    @Operation(summary = "Create a new tennis match licence for this customerId")
    public Response createLicenceForMatch(@Context final UriInfo uriInfo, @PathParam final UUID customerId,
                                          @Valid final CreateLicenceRequest createLicenceRequest) {
        log.debug("Received request to create match licence for customer - {} for match - {}", customerId, createLicenceRequest.getEventId());
        customerService.createLicenceForMatch(customerId, createLicenceRequest);
        return createdResponse(uriInfo);
    }

    @PATCH
    @Path("/{customerId}/licence/tournament")
    @Operation(summary = "Create a new tennis tournament licence for this customerId")
    public Response createLicenceForTournament(@Context final UriInfo uriInfo, @PathParam final UUID customerId,
                                               @Valid final CreateLicenceRequest createLicenceRequest) {
        log.debug("Received request to create tournament licence for customer - {} for tournament - {}", customerId, createLicenceRequest.getEventId());
        customerService.createLicenceForTournament(customerId, createLicenceRequest);
        return createdResponse(uriInfo);
    }

}