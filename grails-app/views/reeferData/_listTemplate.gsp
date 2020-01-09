<div class="table-responsive">
    <table class="table table-hover table-outline table-vcenter card-table table-custom">
        <thead class="thead-table-custom">
          <g:sortableColumn class="th-table-custom" property="satelliteId" title="Satellite Id" />
          <g:sortableColumn class="th-table-custom" property="containerId" title="Container Id" />
          <g:sortableColumn class="th-table-custom" property="containerManufacturer" title="Type" />
          <g:sortableColumn class="th-table-custom" property="setTemperature" title="Set Point" />

          <g:sortableColumn class="th-table-custom" property="supplyTemperature" title="Supply Temp" />

          <g:sortableColumn class="th-table-custom" property="returnTemperature" title="Return Temp" />

          <g:sortableColumn class="th-table-custom" property="humidity" title="Humidity" />

          <g:sortableColumn class="th-table-custom" property="usda1" title="USDA1" />

          <g:sortableColumn class="th-table-custom" property="usda2" title="USDA2" />

          <g:sortableColumn class="th-table-custom" property="usda3" title="USDA3" />

          <g:sortableColumn class="th-table-custom" property="setO2" title="Set O2" />

          <g:sortableColumn class="th-table-custom" property="actO2" title="Act O2" />

          <g:sortableColumn class="th-table-custom" property="setCO2" title="Set CO2" />

          <g:sortableColumn class="th-table-custom" property="actCO2" title="Act CO2" />

          <g:sortableColumn class="th-table-custom" property="alarm" title="Alarm" />

          <g:sortableColumn class="th-table-custom" property="takenTime" title="Taken Time (UTC)" />

          <g:sortableColumn class="th-table-custom" property="latitude" title="Latitude" />

          <g:sortableColumn class="th-table-custom" property="longitude" title="Longitude" />

          <g:sortableColumn class="th-table-custom" property="emailTime" title="Sent Time (UTC)"  style="width: 50px" />

        </thead>
        <tbody>
            <g:each in="${reeferDataList}" var="reefer" status="i">

                <tr class="${(i % 2) == 0 ? 'even' : 'odd'} tr-table-custom">
                    <td class="td-table-custom">
                        <div>
                            <g:link method="GET" resource="${reefer}">
                                ${reefer.satelliteId}
                            </g:link>
                        </div>
                    </td>

                      <g:if test="${reefer.containerId == null || reefer.containerId.length() == 0}">
                          <td class="td-table-custom">
                              <div>
                                  ${fieldValue(bean:reefer, field:'remarks')}
                              </div>
                          </td>
                      </g:if>

                      <g:else>
                              <td class="td-table-custom">
                                  <div>
                                      ${fieldValue(bean:reefer, field:'containerId')}
                                  </div>
                              </td>
                      </g:else>
                    <td class="td-table-custom">${fieldValue(bean:reefer, field:'containerManufacturer')}</td>

                    <td class="td-table-custom">
                      <g:if test="${reefer.setTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'setTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if>
                    </td>

                    <td class="td-table-custom"><g:if test="${reefer.supplyTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'supplyTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.returnTemperature != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'returnTemperature')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.humidity != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'humidity')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.usda1 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda1')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.usda2 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda2')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.usda3 != null }">
                        <g:formatNumber number="${fieldValue(bean:reefer, field:'usda3')}" type="number" minFractionDigits="2"/>
                      </g:if></td>

                    <td class="td-table-custom"><g:if test="${reefer.setO2 != null }">
                        <div> 
                          <div>
                            ${fieldValue(bean:reefer, field:'setO2')}
                          </div>
                        </div>
                      </g:if>
                    </td>

                    <td class="td-table-custom">
                      <g:if test="${reefer.actO2 != null }">
                        <div>
                          <div>
                            ${fieldValue(bean:reefer, field:'actO2')}
                          </div>
                        </div>
                      </g:if>
                    </td>

                    <td class="td-table-custom">
                      <g:if test="${reefer.setCO2 != null }">
                        <div>
                          <div>
                            ${fieldValue(bean:reefer, field:'setCO2')}
                          </div>
                        </div>
                      </g:if>
                    </td>

                    <td class="td-table-custom">
                      <g:if test="${reefer.actCO2 != null }">
                        <div>
                          <div>
                            ${fieldValue(bean:reefer, field:'actCO2')}
                          </div>
                        </div>
                      </g:if>
                    </td>

                    <td class="td-table-custom">${fieldValue(bean:reefer, field:'alarm')}</td>

                    <td class="td-table-custom"><g:formatDate format="yyyy-MM-dd HH:mm" date="${reefer.takenTime}"/></td>

                    <td class="td-table-custom">${fieldValue(bean:reefer, field:'latitude')}</td>

                    <td class="td-table-custom">${fieldValue(bean:reefer, field:'longitude')}</td>

                    <td class="td-table-custom"><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${reefer.emailTime}"/></td>


                </tr>

            </g:each>
        </tbody>
    </table>
</div>
