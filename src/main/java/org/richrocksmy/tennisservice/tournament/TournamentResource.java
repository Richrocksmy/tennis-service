package org.richrocksmy.tennisservice.tournament;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.richrocksmy.tennisservice.common.ResponseBuilder.createdResponse;

@Path("/v1/tournaments")
@Tag(name = "Tournament Resource")
public class TournamentResource {

    private TournamentService tournamentService;

    public TournamentResource(final TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @POST
    @Operation(summary = "Create a new tennis tournatment")
    public Response createTournament(@Context final UriInfo uriInfo, final CreateTournamentRequest createTournamentRequest) {
        return createdResponse(uriInfo, tournamentService.createTournament(createTournamentRequest));
    }

}