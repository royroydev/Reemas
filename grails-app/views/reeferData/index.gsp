<!DOCTYPE html>
<html >
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'reeferData.label', default: 'ReeferData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <g:javascript>
          var timer;
          function showSpinner() {
          document.getElementById('spinner').style.display = 'inline';
          }

          function hideSpinner() {
          document.getElementById('spinner').style.display = 'none';
          }

          function startup() {
          timer = setInterval("refresh()", 60000);
          }
          
          function closeup() {
          clearInterval(timer);
          }

          function refresh() {
          <g:remoteFunction controller="reeferData" action="index" update="updateTable" onLoading="showSpinner()" onComplete="hideSpinner()" params="${params}" />
          }
          window.onload = function() {
            startup();
          };
          
        </g:javascript>
    </head>
    <body >


      <div class="col-12 table-long">

          <div class="card">
              <div class="card-header">
                  <h3 class="card-title">Reefer Monitoring Data Server 2</h3>
                  <div class="card-options">
                      <form controller="refeerData" method="GET">
                          <div class="input-group">
                              <input type="text" class="form-control form-control-sm" placeholder="Search something..." name="query">
                              <span class="input-group-btn ml-2">
                                  <button class="btn btn-sm btn-default" type="submit">
                                    <span class="fe fe-search"></span>
                                  </button>
                                </span>
                          </div>
                      </form>
                  </div>
              </div>
              
              <g:if test="${flash.message}">
                  <div class="card-alert alert alert-success mb-0">
                      ${flash.message}
                  </div>
              </g:if>

              <div id="updateTable">
                <g:render template="listTemplate" bean="${reeferData}" />

              </div>
              <div class="card-footer">
                  <div class="pagination">
                      <g:paginate total="${reeferDataCount ?: 0}" />
                  </div>
              </div>

          </div>
      </div>
    </body>
</html>
