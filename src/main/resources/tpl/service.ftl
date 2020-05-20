<#if moduleName == serverName>
package app.${moduleName}.service;
<#else>
package app.${serverName}.${moduleName}.service;
</#if>

import app.${serverName}.api.${moduleName}.Create${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Get${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}DetailResponse;
import app.${serverName}.api.${moduleName}.Update${moduleName?cap_first}Request;
<#if moduleName == serverName>
import app.${moduleName}.domain.${moduleName?cap_first};
<#else>
import app.${serverName}.${moduleName}.domain.${moduleName?cap_first};
</#if>
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import static java.util.stream.Collectors.toList;

/**
 * @author ebin
 */
public class ${moduleName?cap_first}Service {
    @Inject
    Repository<${moduleName?cap_first}> ${moduleName}Repository;

    public void create(Create${moduleName?cap_first}Request request) {
        ${moduleName?cap_first} ${moduleName} = new ${moduleName?cap_first}();
        ${moduleName}Repository.insert(${moduleName});
    }

    public Get${moduleName?cap_first}Response get(Long id) {
        ${moduleName?cap_first} ${moduleName} = ${moduleName}Repository.get(id).orElseThrow(() -> new NotFoundException("${moduleName} not found, id=" + id));
        return get${moduleName?cap_first}Response(${moduleName});
    }

    public Search${moduleName?cap_first}Response search(Search${moduleName?cap_first}Request request) {
        Query<${moduleName?cap_first}> query = ${moduleName}Repository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        Search${moduleName?cap_first}Response response = new Search${moduleName?cap_first}Response();
        response.total = query.count();
        response.${moduleName}s = query.fetch().stream().map(this::search${moduleName?cap_first}DetailResponse).collect(toList());
        return response;
    }

    public void update(Long id, Update${moduleName?cap_first}Request request) {
        ${moduleName?cap_first} ${moduleName} = ${moduleName}Repository.get(id).orElseThrow(() -> new NotFoundException("${moduleName} not found, id=" + id));
        ${moduleName}Repository.partialUpdate(${moduleName});
    }

    public void delete(Long id) {
        ${moduleName}Repository.delete(id);
    }

    private Search${moduleName?cap_first}DetailResponse search${moduleName?cap_first}DetailResponse(${moduleName?cap_first} ${moduleName}) {
        Search${moduleName?cap_first}DetailResponse response = new Search${moduleName?cap_first}DetailResponse;
        return response;
    }
}
