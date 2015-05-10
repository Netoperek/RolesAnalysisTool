<html>
<head>
    <#include "head.ftl">
</head>
    <body>
    <div class="jumbotron">
        <h1>Upload file with graph</h1><br />
        <form method="POST" action="/uploadGraph/upload" enctype="multipart/form-data">
            <input type="file" name="file"><br />
            <input class="btn btn-primary btn-lg" type="submit" value="Upload">
        </form>
    </div>
    </body>
</html>