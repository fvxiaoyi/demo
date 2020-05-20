<#if moduleName == serverName>
package app.${moduleName}.web;
<#else>
package app.${serverName}.${moduleName}.web;
</#if>

import app.${serverName}.api.${moduleName?cap_first}WebService;
import app.${serverName}.api.${moduleName}.Create${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Get${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Request;
import app.${serverName}.api.${moduleName}.Search${moduleName?cap_first}Response;
import app.${serverName}.api.${moduleName}.Update${moduleName?cap_first}Request;
<#if moduleName == serverName>
import app.${moduleName}.service.${moduleName?cap_first}Service;
<#else>
import app.${serverName}.${moduleName}.service.${moduleName?cap_first}Service;
</#if>
import core.framework.inject.Inject;

/**
 * @author ebin
 */
public class ${moduleName?cap_first}WebServiceImpl implements ${moduleName?cap_first}WebService {
    @Inject
    ${moduleName?cap_first}Service ${moduleName}Service;

    @Override
    public void create(Create${moduleName?cap_first}Request request) {
        ${moduleName}Service.create(request);
    }

    @Override
    public Get${moduleName?cap_first}Response get(Long id) {
        return ${moduleName}Service.get(id);
    }

    @Override
    public Search${moduleName?cap_first}Response search(Search${moduleName?cap_first}Request request) {
        return ${moduleName}Service.search(request);
    }

    @Override
    public void update(Long id, Update${moduleName?cap_first}Request request) {
        ${moduleName}Service.update(id, request);
    }
}
