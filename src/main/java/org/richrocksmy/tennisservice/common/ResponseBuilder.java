package org.richrocksmy.tennisservice.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

public class ResponseBuilder {

    public static Response createdResponse(final UriInfo uriInfo) {
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
    }

    public static Response createdResponse(final UriInfo uriInfo, final UUID id) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(id.toString());

        return Response.created(uriBuilder.build()).build();
    }
}
