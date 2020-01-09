<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'appUser.label', default: 'AppUser')}" />
    <title>
        <g:message code="default.edit.label" args="[entityName]" />
    </title>
</head>

<body>

    <div class="col-md-12">

        <div class="row">
            <g:form resource="${this.appUser}" method="PUT" class="card">

                <div class="card-header">
                    <h3 class="card-title">
                        <g:message code="default.edit.label" args="[entityName]" />
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

                <g:hasErrors bean="${this.appUser}">
                    <div class="card-alert alert alert-info mb-0">
                        <ul>
                            <g:eachError bean="${this.appUser}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </div>
                </g:hasErrors>

                <div class="card-body row">
                    <g:hiddenField name="version" value="${this.appUser?.version}" />
                    <f:all bean="appUser"  />
                    <div class="col-md-6 col-lg-4">
                        <div class="form-group ">
                            <label for="role" >Role</label>
                            <g:select class="form-control" optionKey="id" name="role" from="${roles}" optionValue="authority" value="${roleUser.appRole.id}"/>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <input class="btn btn-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </div>

            </g:form>
        </div>
    </div>


</body>

</html>
