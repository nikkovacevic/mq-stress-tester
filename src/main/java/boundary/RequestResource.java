package boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import model.RequestDTO;
import service.RequestService;

@Path("/api")
@ApplicationScoped
public class RequestResource {

    @Inject
    RequestService requestService;

    @POST
    public Response startTesting(RequestDTO requestDTO) {
        requestService.sendMessages(requestDTO);
        return Response.ok().build();
    }
}

