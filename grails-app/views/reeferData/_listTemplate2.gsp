<div class="table-responsive " >
    <table class="table table-hover table-outline table-vcenter card-table table-custom ">
        <thead class="thead-table-custom">
          <g:sortableColumn property="satelliteId" title="Satellite Id" />
          <g:sortableColumn property="containerId" title="Container Id"/>
          <g:sortableColumn property="containerManufacturer" title="Type" />
          <g:sortableColumn property="setTemperature" title="Set Point" class="text-center" />

          <g:sortableColumn property="supplyTemperature" title="Supply Temp" class="text-center" />

          <g:sortableColumn property="returnTemperature" title="Return Temp" class="text-center" />

          <g:sortableColumn property="humidity" title="Humidity" class="text-center" />

          <g:sortableColumn property="usda1" title="USDA1"  class="text-center"/>

          <g:sortableColumn property="usda2" title="USDA2"  class="text-center"/>

          <g:sortableColumn property="usda3" title="USDA3" class="text-center"/>

          <th class="sortable">
            <g:link uri="/reeferData/index" params="[sort: 'setO2', max: 100, order: 'asc']">Set O2</g:link>
            <br>
            <g:link uri="/reeferData/index" params="[sort: 'actO2', max: 100, order: 'desc']">Act O2</g:link>
          </th>
          <th class="sortable">
            <g:link uri="/reeferData/index" params="[sort: 'setCO2', max: 100, order: 'asc']">Set CO2</g:link>
            <br>
            <g:link uri="/reeferData/index" params="[sort: 'actCO2', max: 100, order: 'desc']">Act CO2</g:link>
          </th>


          <g:sortableColumn property="alarm" title="Alarm" />

          <g:sortableColumn property="takenTime" title="Taken Time (UTC)" />

          <th class="sortable">
            <g:link uri="/reeferData/index" params="[sort: 'latitude', max: 100, order: 'asc']">Latitude</g:link>
            <br>
            <g:link uri="/reeferData/index" params="[sort: 'latitude', max: 100, order: 'desc']">Longitude</g:link>
          </th>

          <g:sortableColumn property="emailTime" title="Sent Time (UTC)" />

        </thead>
        <tbody>
            <g:each in="${reeferDataList}" var="reefer" status="i">

                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>
                        <div>
                            <g:link method="GET" resource="${reefer}">
                                ${reefer.satelliteId}
                            </g:link>
                        </div>
                    </td>

                      <g:if test="${reefer.containerId == null || reefer.containerId.length() == 0}">
                          <td>
                              <div>
                                  ${fieldValue(bean:reefer, field:'remarks')}
                              </div>
                          </td>
                      </g:if>

                      <g:else>
                              <td>
                                  <div>
                                      ${fieldValue(bean:reefer, field:'containerId')}
                                  </div>
                              </td>
                      </g:else>
                    
                    <td>${fieldValue(bean:reefer, field:'containerManufacturer')}</td>

                    <td class="text-center">
                      <g:if test="${reefer.setTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'setTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.supplyTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'supplyTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.returnTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'returnTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.humidity != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'humidity')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.usda1 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda1')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.usda2 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda2')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="text-center">
                      <g:if test="${reefer.usda3 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda3')}" type="number" minFractionDigits="2"/>
                      </g:if>

                    </td>

                    <td>
                      <g:if test="${reefer.setO2 != null }">
                        <div>
                          <span class="text-muted small">Set O2:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'setO2')}
                          </div>
                        </div>
                      </g:if>

                      <g:if test="${reefer.actO2 != null }">
                        <div>
                          <span class="text-muted small">Act O2:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'actO2')}
                          </div>
                        </div>
                      </g:if>

                    </td>

                    <td>
                      <g:if test="${reefer.setCO2 != null }">
                        <div>
                          <span class="text-muted small">Set CO2:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'setCO2')}
                          </div>
                        </div>
                      </g:if>

                      <g:if test="${reefer.actCO2 != null }">
                        <div>
                          <span class="text-muted small">Act CO2:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'actCO2')}
                          </div>
                        </div>
                      </g:if>

                    </td>

                    <td>${fieldValue(bean:reefer, field:'alarm')}</td>

                    <td><g:formatDate format="yyyy-MM-dd HH:mm" date="${reefer.takenTime}"/></td>

                    <td>
                      <g:if test="${reefer.latitude != null }">
                        <div>
                          <span class="text-muted small">Latitude:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'latitude')}
                          </div>
                        </div>
                      </g:if>

                      <g:if test="${reefer.longitude != null }">
                        <div>
                          <span class="text-muted small">Longitude:</span>
                          <div>
                            ${fieldValue(bean:reefer, field:'longitude')}
                          </div>
                        </div>
                      </g:if>
                    </td>

                    <td class="text-wrap"><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${reefer.emailTime}"/></td>

                </tr>

            </g:each>
        </tbody>
    </table>
</div>


