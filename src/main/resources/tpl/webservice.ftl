package app.${serverName}.api;

import app.${serverName}.api.${moduleName}.Create${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Get${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Update${moduleName?cap_first}Request;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author ebin
 */
public interface ${moduleName?cap_first}WebService {
    @POST
    @Path("/${moduleName}")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(Create${moduleName?cap_first}Request request);

    @GET
    @Path("/${moduleName}/:id")
    Get${moduleName?cap_first}Response get(@PathParam("id") Long id);

    @GET
    @Path("/${moduleName}")
    Search${moduleName?cap_first}Response search(Search${moduleName?cap_first}Request request);

    @PUT
    @Path("/${moduleName}/:id")
    void update(@PathParam("id") Long id, Update${moduleName?cap_first}Request request);
}
