<html>
<head>
<#include "head.ftl">
</head>
</head>
<body>
<#include "navBar.ftl">
<div class="content">
    <div id="file-uploader">
        <div id="file-uploader-panel" class="panel panel-info">
            <#if uploaded>
                <div class="panel-heading">Upload your graph file here -> Uploaded</div>
            <#else >
                <div class="panel-heading">Upload your graph file here</div>
            </#if>

                <div class="panel-body">
                <form method="POST" action="/graphs/upload" enctype="multipart/form-data">
                    <input type="file" name="file"><br/>
                    <input class="btn btn-primary btn-lg" type="submit" value="Upload">
                </form>
            </div>
        </div>
    </div>
<#include "graphsTable.ftl">

</div>
</body>
<#if draw??>
    <#if draw>
    <script src="scripts/graphs.js"></script>
    </#if>
</#if>
</html>