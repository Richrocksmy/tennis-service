package org.richrocksmy.tennisservice.match;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.richrocksmy.tennisservice.common.ResponseBuilder.createdResponse;

@Slf4j
@Path("/v1/matches")
@Tag(name = "Match Resource")
public class MatchResource {

    private MatchService matchService;

    public MatchResource(final MatchService matchService) {
        this.matchService = matchService;
    }

    @POST
    @Operation(summary = "Create a new tennis match")
    public Response createMatch(@Context final UriInfo uriInfo, @Valid final CreateMatchRequest createMatchRequest) {
        log.debug("Received request to create a new tennis match");
        return createdResponse(uriInfo, matchService.createMatch(createMatchRequest));
    }

}