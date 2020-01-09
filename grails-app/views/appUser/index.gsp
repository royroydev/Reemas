<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'appUser.label', default: 'AppUser')}" />
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
                    <div class="card-alert alert alert-info mb-0">
                        ${flash.message}
                    </div>
                </g:if>



                <div class="table-responsive">
                    <table class="table table-hover table-outline table-vcenter text-nowrap card-table">
                        <thead>
                            <g:sortableColumn property="appUser.username" title="Username" />
                            <g:sortableColumn property="appRole.authority" title="Role" />
                            <g:sortableColumn property="appUser.userRealName" title="Real Name" />
                            <g:sortableColumn property="appUser.email" title="Email" />
                            <g:sortableColumn property="appUser.enabled" title="Enabled" />
                            
                        </thead>
                        <tbody>
                            <g:each in="${roleUser}" var="roleU" status="i">
                                <tr>
                                    
                                    <td >
                                        <g:link method="GET" resource="${roleU.appUser}">
                                                ${roleU.appUser.username}
                                        </g:link>
                                        
                                    </td>
                                    <td>
                                        ${roleU.appRole.authority}
                                    </td>
                                    <td>
                                        ${roleU.appUser.userRealName}
                                    </td>
                                    <td>
                                        ${roleU.appUser.email}
                                    </td>
                                    <td>
                                        ${roleU.appUser.enabled}
                                    </td>
                                </tr>

                            </g:each>
                        </tbody>

                    </table>

                </div>


                <div class="card-footer">
                    <div class="pagination">
                        <g:paginate total="${appUserCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
