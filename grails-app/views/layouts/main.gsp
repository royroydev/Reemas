<!doctype html>
<html lang="en" class="no-js" xmlns:g="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="Content-Language" content="en">
    <meta name="msapplication-TileColor" content="#2d89ef">
    <meta name="theme-color" content="#4188c9">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="HandheldFriendly" content="True">


    <title>
        <g:layoutTitle default="reemas"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

    <asset:stylesheet src="application.css"/>


    <g:layoutHead/>
</head>
<body>

<div class="page">
    <div class="page-main">
        <div class="header py-4">
            <div class="container">
                <div class="d-flex">

                    <sec:ifLoggedIn>
                        <div class="d-flex order-lg-2 ml-auto">

                            <div class="dropdown">
                                <a href="#" class="nav-link pr-0 leading-none" data-toggle="dropdown">
                                    <span class="avatar" style='background-image: url("${resource(dir: 'images/', file: 'user-admin.png')}");'></span>
                                    <span class="ml-2 d-none d-lg-block">
                                            <span class="text-default"><sec:loggedInUserInfo field='username'/></span>
                                    </span>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
                                    <g:link controller='reeferData' class="dropdown-item">
                                        <i class="dropdown-icon fe fe-user"></i> Home
                                    </g:link>

                                    <div class="dropdown-divider"></div>
                                    <g:link controller='logout' class="dropdown-item">
                                        <i class="dropdown-icon fe fe-log-out"></i>Logout
                                    </g:link>

                                </div>
                            </div>
                        </div>

                        <a href="#" class="header-toggler d-lg-none ml-3 ml-lg-0" data-toggle="collapse" data-target="#headerMenuCollapse">
                            <span class="header-toggler-icon"></span>
                        </a>
                    </sec:ifLoggedIn>

                    <sec:ifNotLoggedIn>
                        <div class="d-flex order-lg-2 ml-auto">
                            <g:link controller='login' action='auth' class="btn btn-outline-primary btn-md">Login</g:link>
                        </div>
                    </sec:ifNotLoggedIn>

                </div>
            </div>
        </div>

        <sec:ifLoggedIn>
            <div class="header collapse d-lg-flex p-0" id="headerMenuCollapse">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg order-lg-first">
                            <ul class="nav nav-tabs border-0 flex-column flex-lg-row">
                                <li class="nav-item">
                                    <a href="/" class="nav-link "><i class="fe fe-home"></i> Home</a>
                                </li>



                                <li class="nav-item dropdown">
                                    <a href="" class="nav-link" data-toggle="dropdown" href="#" role="button" id="dropdownMenuLinkAlarm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fe fe-bell"></i> Alarm Control</a>

                                    <div class="dropdown-menu dropdown-menu-arrow" aria-labelledby="dropdownMenuLinkAlarm">

                                        <g:link controller='supplyTemp' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Supply Temp
                                        </g:link>

                                        <g:link controller='setPoint' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Set Point
                                        </g:link>

                                        <g:link controller='usda1' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Usda1
                                        </g:link>

                                        <g:link controller='usda2' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Usda2
                                        </g:link>

                                        <g:link controller='usda3' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Usda3
                                        </g:link>

                                        <g:link controller='setO2' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Set O2
                                        </g:link>

                                        <g:link controller='actO2' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Act O2
                                        </g:link>

                                        <g:link controller='setCO2' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Set CO2
                                        </g:link>

                                        <g:link controller='actCO2' action="index" class="dropdown-item">
                                            <i class="fe fe-box"></i>Act CO2
                                        </g:link>


                                    </div>
                                </li>

                                <sec:ifAnyGranted roles='ROLE_ADMIN'>

                                    <li class="nav-item dropdown">
                                        <a href="" class="nav-link" data-toggle="dropdown" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fe fe-file"></i> Manage Users</a>

                                        <div class="dropdown-menu dropdown-menu-arrow" aria-labelledby="dropdownMenuLink">

                                            <g:link controller='appUser' action="index" class="dropdown-item">
                                                <i class="fe fe-user"></i>Users
                                            </g:link>

                                            <g:link controller='appRole' action="index" class="dropdown-item">
                                                <i class="fe fe-users"></i>Roles
                                            </g:link>


                                        </div>
                                    </li>

                                </sec:ifAnyGranted>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </sec:ifLoggedIn>

        <div class="my-3 my-md-5">
            <div class="container">
                <div class="row">
                    <g:layoutBody/>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="container">

        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <div class="row align-items-center flex-row-reverse">

                <div class="col-12 col-lg-auto mt-3 mt-lg-0 text-center"><a href="#"> </a> <a href="#" target="_blank"></a>
                </div>
            </div>
        </div>
    </footer>
</div>




    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
