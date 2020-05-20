package app.${serverName}.api.${moduleName};

import core.framework.api.validate.NotNull;
import core.framework.api.json.Property;

import java.util.List;

/**
 * @author ebin
 */
public class Search${moduleName?cap_first}Response {
    @NotNull
    @Property(name = "total")
    public Long total;

    @Property(name = "${tableName}")
    public List<Search${moduleName?cap_first}DetailResponse> ${tableName};
}
