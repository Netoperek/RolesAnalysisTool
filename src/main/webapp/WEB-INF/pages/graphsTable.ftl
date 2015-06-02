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
                    <#if graphsRoles??>
                        <#assign values = graphsRoles[ele]?values>
                        <#assign keys = graphsRoles[ele]?keys>
                        ${ele}
                        <button onclick="drawGraph('${ele}', '${keys?join(", ", "-")}', '${values?join(", ", "-")}')" type="submit" class="btn btn-info">
                            <span class="glyphicon glyphicon-menu-left"></span>
                        </button>
                    </#if>
                    <#if graphsEdges??>
                        <#assign edges = graphsEdges[ele]>
                        <a href="#" onclick="toggle_visibility('edges');">Toggle Edges</a>
                        <div id="edges">
                        <#list edges as edge>
                            <p> ${edge} </p>
                        </#list>
                        </div>
                    </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
        <#list graphsRoles?keys as prop>
            <#assign keys = graphsRoles[prop]?keys>

        </#list>
    </div>

    <#if typeDisplayed == 'WITH_ROLES'>
        <div id="rolesDescription">
            <p id="STANDARD" style="visibility:hidden">STANDARD</p>
            <p id="MEDIATOR" style="visibility:hidden">MEDIATOR</p>
            <p id="INFLUENTIAL" style="visibility:hidden">INFLUENTIAL</p>
        </div>
    </#if>

    <button class="btn btn-default" href="#">Switch to structural</button>
</div>
</#if>