<#if graphsFiles??>
<div id="grahps-list">
    <div class="panel panel-default">
        <div class="panel-heading">Current uploaded graphs</div>
        <table class="table">
            <thead>
            <th>
                #
            </th>
            <th>
                File name
            </th>
            </thead>
            <tbody>
                <#list graphsFiles as ele>
                <tr>
                    <td>
                        ${ele_index + 1}
                    </td>
                    <td class="graph-row">
                        <#assign values = graphsRoles[ele]?values>
                        <#assign keys = graphsRoles[ele]?keys>
                        ${ele}
                        <button onclick="drawGraph('${ele}', '${keys?join(", ", "-")}', '${values?join(", ", "-")}')" type="submit" class="btn btn-info">
                            <span class="glyphicon glyphicon-menu-left"></span>
                        </button>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
        <#list graphsRoles?keys as prop>
            <#assign keys = graphsRoles[prop]?keys>

        </#list>
    </div>
</div>
</#if>