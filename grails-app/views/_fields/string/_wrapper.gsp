<div class="col-md-6 col-lg-4">
    <div class="form-group ${invalid ? 'has-error' : ''}">
        <label for="${property}">${label}
            <span class="form-required">${required ? '*' : ''}</span>
        </label>
        <f:widget property="${property}" />

        <g:if test="${errors}">
            <g:each in="${errors}" var="error">
                <div class="invalid-feedback">
                    <g:message error="${error}" />
                </div>
            </g:each>
        </g:if>
    </div>
</div>
