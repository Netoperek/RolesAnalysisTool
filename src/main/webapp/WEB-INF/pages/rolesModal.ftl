<div id="openRolesModal" class="modalDialog">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <div class="panel-body">
            <form method="POST" action="/graphs/updateRoles" enctype="multipart/form-data">
                Mediators percentage <input type="number" name="mediator"  min="0" max="100" required><br/><br/>
                Influential percentage <input type="number" name="influential" min="0" max="100" required><br/><br/>
                <input class="btn btn-primary btn-lg" type="submit" value="Upload">
            </form>
        </div>
    </div>
</div>