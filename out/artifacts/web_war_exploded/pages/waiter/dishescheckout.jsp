<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>${ORDER_SYS_NAME}-餐厅管理员</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="bootstrap/js/jquery-1.11.1.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/ajax.js"></script>
    <title>服务员点餐界面</title>
    <script type="text/javascript">

        function getOrderList(page) {

            xmlAjaxRequest("getcheckoutd.order?page=" + page + "&time="
                + Math.random(), "get", true, null, showList, null, null);
            txtAjaxRequest("getordermsg2.order?messageTitle=tableId&time="
                + Math.random(), "get", true, null, reload, null, null);
            txtAjaxRequest("getrtbord.order?messageTitle=rtbord&time="
                +Math.random(),"get",true,null,bordCallback,null,null);
            txtAjaxRequest("getordermsg.order?messageTitle=ordermsg&time="
                +Math.random(),"get",true,null,bordCallback,null,null);
        }

        function bordCallback(responseTxt, obj) {
            if (responseTxt != "Org_EricYang_Platform_ErrorMsg:ServerPush_Wait_TimeOut") {
                var msg = document.getElementById("msg");

                msg.innerHTML = responseTxt;

            }
            txtAjaxRequest("getrtbord.order?messageTitle=rtbord&time="
                + Math.random(), "get", true, null, bordCallback, null, null);
            txtAjaxRequest("getordermsg.order?messageTitle=ordermsg&time="
                +Math.random(),"get",true,null,bordCallback,null,null);

        }

//        function getOrderList() {
//
//            xmlAjaxRequest("tooperatedatashow.order?startTime=" + stattime + "&endTime="+ endtime +"&time="
//                + Math.random(), "get", null, true, showList, null, null);
//            txtAjaxRequest("getordermsg2.order?messageTitle=tableId&time="
//                + Math.random(), "get", true, null, reload, null, null);
//        }

        //        显示列表局部刷新
        function reload(responseTxt,obj) {
            if (responseTxt != "Org_EricYang_Platform_ErrorMsg:ServerPush_Wait_TimeOut") {
                if(responseTxt != null){
                    $("table").load(getOrderList(1));
                }

            }
            txtAjaxRequest("getordermsg2.order?messageTitle=tableId&time="
                + Math.random(), "get", true, null, reload, null, null);
        }

        function DeleteDishesOrder(obj,id) {

            if (confirm("您真的要买单吗？")) {
                xmlAjaxRequest("deletedishesorder.order?orderId=" + id + "&time=" + Math.random(), "get", true, null, deleteCallback, obj, null);

            }
            xmlAjaxRequest("sendordermsgtoadmin.order?orderId=" + id + "&time="
                + Math.random(), "get", true, null, null, null, null);

        }

        function deleteCallback(responseTxt, obj) {

            alert("已提交管理员！");
            obj.parentNode.parentNode.parentNode
                .removeChild(obj.parentNode.parentNode);
        }




        function showList(responseXml, obj) {

            var table = document.getElementById("orderTable");
            table.innerHTML = "";
            var orders = responseXml.getElementsByTagName("order");
            for (var i = 0; i < orders.length; i++) {
                var operata = orders[i];
                var attrs = operata.childNodes;
                var orderId;
                var orderBeginDate;
                var orderEndDate;
                var waiterId;
                var orderState;
                var tableId;
                for (var j = 0; j < attrs.length; j++) {
                    var attr = attrs[j];
                    if (attr.nodeName == "orderId") {
                        orderId = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "orderBeginDate") {
                        orderBeginDate = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "orderEndDate") {
                        orderEndDate = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "waiterId") {
                        waiterId = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "orderState") {
                        orderState = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "tableId") {
                        tableId = attr.childNodes[0].nodeValue;
                    }
                }
                var newLine = "<tr><td>" + tableId + "</td><td>" + waiterId + "</td><td>" + orderBeginDate + "</td>";

                newLine += "<td>";
//                newLine += "<i style='cursor: pointer; font-size: 14;'";
//                newLine += "onmouseover='this.style.color=\"orange\"'";
//                newLine += "onmouseout='this.style.color=\"black\"'";
//                newLine += "class='icon-sitemap icon-large' title='查看经营数据'></i>&nbsp;&nbsp;";
                newLine += "<input type='submit' class='btn btn-danger'  onclick='DeleteDishesOrder(this," + orderId + ")' style='width: 350px' value='买单' />"
                newLine += "</td></tr>";

                table.innerHTML += newLine;
            }
        }

    </script>
</head>
<body onload="getOrderList(1)">
<%--导航栏--%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                    aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <img src="img/logo.png" class="navbar-brand" /> <span
                class="navbar-brand">${ORDER_SYS_NAME}</span>
        </div>

        <div id="navbar" class="navbar-collapse collapse ">
            <ul class="nav navbar-nav navbar-right">
                <li><span class="navbar-brand">餐厅管理界面</span></li>
                <li class="dropdown"><a href="#" class="dropdown-toggle"
                                        data-toggle="dropdown" role="button" aria-haspopup="true"
                                        aria-expanded="false"><img
                        src="img/faces/${USER_INFO.faceimg }" width="24" height="24"
                        class="img-circle" style="border:1px solid #FFF" />&nbsp;&nbsp;当前用户:【${USER_INFO.userAccount}】,
                    身份为${USER_INFO.roleName}
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li style="text-align: center;padding-top: 15px"><img
                                src="img/faces/${USER_INFO.faceimg }" width="128" height="128"
                                class="img-circle"
                                style="border:1px solid #CCC;box-shadow:0 0 10px rgba(100, 100, 100, 1);margin-bottom: 20px" /></li>
                        <li><a href="tomodifymypassforwaiter.order?userId=${USER_INFO.userId
                        }">修改我的密码</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="toonlinekitchen.order">查看当前在线的厨师 </a></li>
                        <li><a href="toonlinewaiters.order">查看当前在线的点餐员</a></li>
                    </ul>
                </li>
                <li><a href="logout.order">退出登录</a></li>
            </ul>
        </div>

    </div>
</nav>

<div class="container-fluid">
    <div class="row">


        <%--左侧导航栏--%>
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="nav-header" style="font-size: 17px;margin-bottom: 10px;margin-left: 10px"><i class="icon-credit-card icon-large"></i>&nbsp;服务员点餐功能</li>
                <li><a href="towaitermain.order"><i class="icon-group icon-large"></i>&nbsp;服务点餐界面</a></li>
                <li class="active"><a href="todishescheckout.order"><i class="icon-coffee icon-large"></i>&nbsp;餐桌结账</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <ol class="breadcrumb">
                <li><a href="/OrderSys/">首页</a></li>
                <li>服务员</li>
                <li class="active">买单界面</li>
            </ol>
            <div class="panel panel-danger">
                <div class="panel-heading"><h3 class="panel-title">最新公告消息</h3></div>
                <div class="panel-body"><span class="glyphicon glyphicon-envelope" style="color: #ff8b1e;font-size: 20px" id="msg"></span></div>
            </div>
            <div class="panel panel-danger">
                <div class="panel-heading"><h3 class="panel-title">顾客点餐列表</h3></div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>桌号</th>
                                <th>服务员</th>
                                <th>开餐时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="orderTable">
                            </tbody>
                        </table>
                    </div>

                    <nav>
                        <ul class="pager">
                            <li><a  id="firstpage">&larr;首页</a></li>
                            <li><a  id="pre">上一页</a></li>
                            <li><a  id="next">下一页</a></li>
                            <li><a  id="last">末页&rarr;</a></li>
                        </ul>
                    </nav>

                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>
