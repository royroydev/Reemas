<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'actO2.label', default: 'ActO2')}" />
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
                    <div class="card-alert alert alert-success mb-0">
                        ${flash.message}
                    </div>
                </g:if>

                <div class="card-body">
                    <f:display bean="actO2" />

                </div>
                <div class="card-footer">
                    <div class="d-flex">
                        <g:form resource="${this.actO2}" method="DELETE" class="d-flex">

                            <g:link class="btn btn-success" action="edit" resource="${this.actO2}">
                                <g:message code="default.button.edit.label" default="Edit" />
                            </g:link>

                            <input class="btn btn-danger ml-3" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </g:form>

                    </div>

                </div>
            </div>
        </div>
    </div>
</body>

</html>
