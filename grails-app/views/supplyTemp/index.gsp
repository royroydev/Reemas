<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'supplyTemp.label', default: 'SupplyTemp')}" />
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
                  
                    <f:table collection="${supplyTempList}" />

                </div>
                <div class="card-footer">
                    <div class="pagination">
                        <g:paginate total="${supplyTempCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
