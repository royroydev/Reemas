<!DOCTYPE html>
<html xmlns:g="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'reeferData.label', default: 'ReeferData')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>


    <div class="col-12">

        <div class="card">
            <div class="card-header">
                <h3 class="card-title">Upload File</h3>

            </div>

            <g:hasErrors bean="${this.cmd}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.cmd}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <div class="card-body">



                <g:uploadForm name="featuredFile" action="uploadFile">
                    <div class="form-group">
                        <div class="form-label">Choose file </div>
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" name="featuredFile">
                            <label class="custom-file-label">Choose file</label>
                        </div>
                    </div>
                    <div class="form-footer">
                        <button type="submit" class="btn btn-primary btn-block">Upload</button>
                    </div>

                </g:uploadForm>

            </div>

            <div class="card-footer">

            </div>
        </div>
    </div>

</body>
</html>
