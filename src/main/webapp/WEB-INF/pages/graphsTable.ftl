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
                        <button onclick="drawGraph('${ele}')" type="submit" class="btn btn-info">
                            <span class="glyphicon glyphicon-menu-left"></span>
                        </button>
                        ${ele}
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#if>