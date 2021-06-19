package org.richrocksmy.tennisservice.common;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class ResponseBuilderTest {

    @Test
    public void shouldReturnCreatedResponseWithUriPathAndId() {
        UriInfo uriInfo = mock(UriInfo.class);
        UriBuilder uriBuilder = mock(UriBuilder.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);

        UUID id = UUID.randomUUID();
        URI uri = URI.create("this/is/a/path/" + id);
        when(uriBuilder.build()).thenReturn(uri);

        Response response = ResponseBuilder.createdResponse(uriInfo, id);

        assertThat(response.getLocation().toString()).isEqualTo("this/is/a/path/" + id);
    }

    @Test
    public void shouldReturnCreatedResponseWithUriPathAndNoId() {
        UriInfo uriInfo = mock(UriInfo.class);
        UriBuilder uriBuilder = mock(UriBuilder.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);

        URI uri = URI.create("this/is/a/path/");
        when(uriBuilder.build()).thenReturn(uri);

        Response response = ResponseBuilder.createdResponse(uriInfo);

        assertThat(response.getLocation().toString()).isEqualTo("this/is/a/path/");
    }

}