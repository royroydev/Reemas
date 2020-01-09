<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'appUserAppRole.label', default: 'AppUserAppRole')}" />
    <title>
        <g:message code="default.list.label" args="[entityName]" />
    </title>
</head>

<body>
    <div class="col-md-12">

        <div class="row">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">
                        <g:message code="default.list.label" args="[entityName]" />
                    </h3>
                    <div class="card-options">
                      <g:link class="btn btn-primary btn-sm" style="color:#fff;" action="create">
                          <g:message code="default.new.label" args="[entityName]" /></g:link>
                    </div>
                </div>

                <g:if test="${flash.message}">
                    <div class="card-alert alert alert-success mb-0">
                        ${flash.message}
                    </div>
                </g:if>

                <div class="card-body">

                    <table class="table table-hover table-outline table-vcenter text-nowrap card-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Role</th>
                            </tr>

                        </thead>
                        <tbody>
                            <g:each in="${appUserAppRoleList}" var="userRo" status="i">

                                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                    <td>
                                        <div>
                                            <g:link method="GET" controller="appUserAppRole" action="show" params="${[idUser: userRo.appUser.id, idRole: userRo.appRole.id]}">
                                                ${userRo.appUser.username}
                                            </g:link>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            ${userRo.appRole.authority}
                                        </div>
                                    </td>
                                </tr>

                            </g:each>
                        </tbody>
                    </table>

                </div>
                <div class="card-footer">
                    <div class="pagination">
                        <g:paginate total="${appUserAppRoleCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
