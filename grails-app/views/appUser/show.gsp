<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'appUser.label', default: 'AppUser')}" />
    <title>
        <g:message code="default.show.label" args="[entityName]" />
    </title>
</head>

<body>

    <div class="col-md-12">

        <div class="row">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">
                        <g:message code="default.show.label" args="[entityName]" />
                    </h3>

                    <div class="card-options">

                        <g:link class="btn btn-secondary btn-sm ml-2" style="color: #495057;" action="index">
                            <g:message code="default.list.label" args="[entityName]" /></g:link>

                        <g:link class="btn btn-primary btn-sm" style="color:#fff;" action="create">
                            <g:message code="default.new.label" args="[entityName]" /></g:link>

                    </div>
                </div>

                <g:if test="${flash.message}">
                    <div class="card-alert alert alert-info mb-0">
                        ${flash.message}
                    </div>
                </g:if>

                <div class="card-body">
                    <f:display bean="appUser"  except="password" />
                    <div class="col-md-6 col-lg-4">
                        <div class="form-group ">
                            <label for="role" >Role</label>
                            <div class="form-control-plaintext" aria-labelledby="username-label">${roleUser.appRole.authority}</div>
                        </div>
                    </div>

                </div>
                <div class="card-footer">
                    <div class="d-flex">
                        <g:form resource="${this.appUser}" method="DELETE">

                            <g:link class="btn btn-primary" action="edit" resource="${this.appUser}">
                                <g:message code="default.button.edit.label" default="Edit" />
                            </g:link>

                            <input class="btn btn-danger ml-auto" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </g:form>

                    </div>

                </div>
            </div>
        </div>
    </div>



</body>

</html>
