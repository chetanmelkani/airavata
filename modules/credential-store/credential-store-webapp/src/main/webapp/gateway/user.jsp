<%--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements. See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership. The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License. You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied. See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<%@ page import="org.apache.airavata.sample.gateway.SampleGateway" %>
<%--
  Created by IntelliJ IDEA.
  User: thejaka
  Date: 7/31/13
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginScreen = request.getParameter("loginScreen");

    String user = (String)session.getAttribute("userName");
    boolean authenticate = false;

    if (loginScreen != null && loginScreen.equals("true")) {
        SampleGateway sampleGateway = null;
        sampleGateway = (SampleGateway) session.getAttribute(SampleGateway.GATEWAY_SESSION);

        if (sampleGateway == null) {
            sampleGateway = new SampleGateway(session.getServletContext());
        }

        session.setAttribute(SampleGateway.GATEWAY_SESSION, sampleGateway);

        user = request.getParameter("username");
        String password = request.getParameter("password");

        authenticate = sampleGateway.authenticate(user, password);
    } else {
        authenticate = true;
    }

%>
<html>

<head>
    <title>Manage</title>
</head>
<body>

<table width="100%" border="0">
    <tr bgcolor="#999999"><td align="right"><a href="user.jsp"><font color="#f5f5f5">Home</font> </a> <a href="logout.jsp"><font color="#f5f5f5">Logout</font></a></td></tr>
</table>

<h1>Sample Gateway</h1>

<%
    if (authenticate) {

        session.setAttribute("userName", user);

        if (SampleGateway.isAdmin(user)) {
%>
<h1>Administration</h1>
<p>
    This page allows administration functionality.
<ol>
    <li><a href="acs.jsp">Retrieve Credentials</a></li>
    <li><a href="list_users.jsp">List Users</a></li>
</ol>
</p>


<%
     } else {
%>

<p> You are a normal user. Click <a href="job.jsp">here</a> to configure and run "Echo" workflow on a GRID machine.</p>

<%
     }
    } else {
%>

<h1>Authentication failed</h1>

<%
    }
%>

</body>
</html>