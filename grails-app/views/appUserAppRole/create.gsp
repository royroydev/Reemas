<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'appUserAppRole.label', default: 'AppUserAppRole')}" />
    <title>
        <g:message code="default.create.label" args="[entityName]" />
    </title>
</head>

<body>

    <div class="col-md-12">

        <div class="row">
            <g:form resource="${this.appUserAppRole}" method="POST" class="card">

                <div class="card-header">
                    <h3 class="card-title">
                        <g:message code="default.create.label" args="[entityName]" />
                    </h3>

                    <div class="card-options">
                        <g:link class="btn btn-secondary btn-sm ml-2" style="color: #495057;" action="index">
                            <g:message code="default.list.label" args="[entityName]" /></g:link>
                    </div>
                </div>

                <g:if test="${flash.message}">
                    <div class="card-alert alert alert-success mb-0">
                        ${flash.message}
                    </div>
                </g:if>

                <g:hasErrors bean="${this.appUserAppRole}">
                    <div class="card-alert alert alert-danger mb-0">
                        <ul>
                            <g:eachError bean="${this.appUserAppRole}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </div>
                </g:hasErrors>

                <div class="card-body">
                  <div class="row">
                    <f:all bean="appUserAppRole" />
                  </div>
                </div>
                <div class="card-footer">
                    <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
            </g:form>
        </div>
    </div>

</body>

</html>
