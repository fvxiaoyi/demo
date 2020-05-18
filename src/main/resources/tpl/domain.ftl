<#if moduleName == serverName>
package app.${serverName}.domain;
<#else>
package app.${serverName}.${moduleName}.domain;
</#if>
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author ebin
 */
@Table(name = "${table.tableName}")
public class ${table.name} {

    <#list fields as field>
    <#if field.name == "id">
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;
    <#else>
    <#if !field.nullable>
    <#if !field.type == "String">
    @NotBlank
    </#if>
    @NotNull
    </#if>
    @Column(name = "${field.columnName}")
    public ${field.type} ${field.name};
    </#if>
    </#list>
}
