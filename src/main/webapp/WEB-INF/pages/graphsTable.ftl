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
                        <#if graphsRoles[ele]??>
                            <#assign values = graphsRoles[ele]?values>
                            <#assign keys = graphsRoles[ele]?keys>
                            ${ele}
                            <button onclick="drawGraph('${ele}', '${keys?join(", ", "-")}', '${values?join(", ", "-")}', '${typeDisplayed}')" type="submit" class="btn btn-info">
                                <span class="glyphicon glyphicon-menu-left"></span>
                            </button>
                        </#if>
                    </#if>
                    <#if graphsEdges??>
                        <#if graphsEdges[ele]??>
                            <#assign edges = graphsEdges[ele]>
                            <a href="#" onclick="toggle_visibility(${ele_index});">Toggle Edges</a>
                            <div id="${ele_index}" class="edges" style="display:none">
                                <table class="table">
                                <thead>
                                    <th>
                                        #
                                    </th>
                                    <th>
                                        Edge
                                    </th>
                                </thead>
                                <tbody>
                                <#list edges as edge>
                                <tr>
                                    <td>
                                        ${edge_index}
                                    </td>
                                    <td>
                                    ${edge}
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                                </table>
                            </div>
                        </#if>
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


    <div id="rolesDescription">
        <#if typeDisplayed == 'WITH_ROLES'>
            <p id="STANDARD" style="visibility:hidden">STANDARD</p>
            <p id="MEDIATOR" style="visibility:hidden">MEDIATOR</p>
            <p id="INFLUENTIAL" style="visibility:hidden">INFLUENTIAL</p>
        </#if>
    </div>


    <a class="btn btn-default" href="/graphs/switchToStructural">Switch to structural</a>
</div>
</#if>