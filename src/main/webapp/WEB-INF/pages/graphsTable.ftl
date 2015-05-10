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
                    <td>
                    ${ele}
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#if>