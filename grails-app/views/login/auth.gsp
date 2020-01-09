<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title><g:message code='springSecurity.login.title'/></title>
</head>

<body>

  <div class="col col-login mx-auto">
    <form autocomplete="none" class="card" action="${postUrl ?: '/login/authenticate'}" method="POST" id="loginForm">

      <div class="card-body p-6">
        <div class="card-title"><g:message code='springSecurity.login.header'/></div>
        <g:if test='${flash.message}'>
          <div class="alert alert-danger" role="alert">
            ${flash.message}
          </div>
        </g:if>
        <div class="form-group">
          <label class="form-label">Username</label>
          <input type="text" class="form-control" name="${usernameParameter ?: 'username'}" id="username" placeholder="Enter username"/>
        </div>
        <div class="form-group">
          <label class="form-label">
            Password
          </label>
          <input type="password" class="form-control" name="${passwordParameter ?: 'password'}" id="password" placeholder="Password"/>
        </div>
        <div class="form-group">
          <label class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
            <span class="custom-control-label">Remember me</span>
          </label>
        </div>
        <div class="form-footer">
          <input type="submit" id="submit" class="btn btn-primary btn-block" value="${message(code: 'springSecurity.login.button')}"/>
        </div>
      </div>
    </form>

  </div>

<script>
(function() {
	document.forms['loginForm'].elements['${usernameParameter ?: 'username'}'].focus();
})();
</script>
</body>
</html>
