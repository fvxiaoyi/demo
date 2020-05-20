package app.${serverName}.api.${moduleName};

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author ebin
 */
public class Search${moduleName?cap_first}Request {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip = 0;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit = 10;
}
