<div class="row">
  <g:each in="${domainProperties}" var="p">
    <div class="col-md-6 col-lg-4">
      <div class="form-group">
        <label class="form-label"><g:message code="${domainClass.decapitalizedName}.${p.name}.label" default="${p.defaultLabel}" /></label>
        <div class="form-control-plaintext" aria-labelledby="${p.name}-label">${body(p)}</div>
      </div>
    </div>
  </g:each>
</div>
