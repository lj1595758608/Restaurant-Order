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
    <style type="text/css">
        .modal-backdrop{
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            z-index: 0;
            background-color: #000;
        }
    </style>
    <script type="text/javascript">
        function createXmlDom(str) {
            if (document.all) {
                var xmlDom = new ActiveXObject("Microsoft.XMLDOM");
                xmlDom.loadXML(str);
                return xmlDom;
            } else {
                return new DOMParser().parseFromString(str, "text/xml");
            }
        }

        function begin() {

            xmlAjaxRequest("toprecommend.order?time=" + Math.random(), "get", true,
                null, getTopRecommendDishes, null, null);
            xmlAjaxRequest("checkout.order?time=" + Math.random(), "get", true,
                null, showOrder, null, null);
            txtAjaxRequest("getrtplay.order?messageTitle=orderId&time="
                + Math.random(), "get", true, null, reload, null, null);


        }

        function reload(responseTxt,obj) {
            if (responseTxt != "Org_EricYang_Platform_ErrorMsg:ServerPush_Wait_TimeOut") {
                if(responseTxt != null){
                    $("table").load(begin());
                }

            }
            txtAjaxRequest("getrtplay.order?messageTitle=orderId&time="
                + Math.random(), "get", true, null, reload, null, null);
        }

        function getTopRecommendDishes(responseXml, obj) {

            var table = document.getElementById("dishesbord");
            table.innerHTML = "";
            var dishes = responseXml.getElementsByTagName("dish");
            for ( var i = 0; i < dishes.length && i < 4; i++) {
                var dish = dishes[i];
                var attrs = dish.childNodes;
                var dishesId = null;
                var dishesName;
                var dishesDiscript;
                var dishesPrice;
                var recommend;
                var dishesImg;
                for ( var j = 0; j < attrs.length; j++) {
                    var attr = attrs[j];
                    if (attr.nodeName == "dishesId") {
                        dishesId = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "dishesName") {
                        dishesName = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "dishesDiscript") {
                        dishesDiscript = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "dishesPrice") {
                        dishesPrice = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "recommend") {
                        recommend = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "dishesImg") {
                        dishesImg = attr.childNodes[0].nodeValue;
                    }
                }

                var subDiscript = dishesDiscript.length > 8 ? dishesDiscript.substring(0, 7) + "..." : dishesDiscript;
                var newLine = "<div class='col-xs-6 col-sm-3 placeholder'>";
                newLine += "<img data-src='holder.js/200x200/auto/vine'";
                newLine += "class='img-thumbnail' alt='Generic placeholder thumbnail'";
                newLine += "style='border-radius:20px;width:95%;height:200px' src='img/dishes/"
                    + dishesImg + "'>";
                newLine += "<h4>" + dishesName + "</h4>";
                newLine += "<span class='text-muted'>" + subDiscript + "</span>";
                newLine += "</div>";

                table.innerHTML += newLine;
            }

        }


        function cancel(obj, orderId) {

            if (confirm("此订单将不结账而直接作废？")) {

                txtAjaxRequest("changeorder.order?orderId=" + orderId
                    + "&state=3&time=" + Math.random(), "get", true, null,
                    callBack, obj, null);

            }
        }
        function pay(obj, orderId) {
            if (confirm("确定结账？")) {

                txtAjaxRequest("checkoutorder.order?orderId=" + orderId
                    + "&state=3&time=" + Math.random(), "get", true, null,
                    callBack, obj, null);

            }
        }


        function callBack(responseTxt, obj) {
            alert("操作成功！");
            obj.parentNode.parentNode.parentNode
                .removeChild(obj.parentNode.parentNode);
        }

        function showOrder(responseXml, obj) {

            var table = document.getElementById("orderTable");
            table.innerHTML = "";
            var order = responseXml.getElementsByTagName("order");
            for ( var i = 0; i < order.length; i++) {
                var orde = order[i];
                var attrs = orde.childNodes;
                var orderId;
                var orderBeginDate;
                var orderEndDate;
                var waiterId;
                var orderstate;
                var tableId;
                var totalPrice;

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
                    if (attr.nodeName == "orderstate") {
                        orderstate = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "tableId") {
                        tableId = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "totalPrice") {
                        totalPrice = attr.childNodes[0].nodeValue;
                    }
                }

                var newLine = "<tr><td>" + tableId + "</td><td>" + orderBeginDate + "</td><td>" + orderEndDate + "</td><td>" + totalPrice + "</td>";
                newLine += "<td>";
                newLine += "<i style='cursor: pointer; font-size: 14;'";
                newLine += "onmouseover='this.style.color=\"orange\"'";
                newLine += "onmouseout='this.style.color=\"black\"'";
                newLine += "class='icon-credit-card icon-large' title='确认结账' onclick='pay(this," + orderId + ")'></i>&nbsp;&nbsp;";

                newLine += "<i style='cursor: pointer; font-size: 14;'";
                newLine += "onmouseover='this.style.color=\"orange\"'";
                newLine += "onmouseout='this.style.color=\"black\"'";
                newLine += "class='icon-sitemap icon-large' onclick='detail(\"" + tableId + "\",\"" + orderBeginDate + "\",\"" + orderEndDate + "\",\"" + waiterId + "\",\"" + totalPrice + "\",\"" + orderId + "\")' title='查看订单详情'></i>&nbsp;&nbsp;";

                newLine += "<i style='cursor: pointer; font-size: 14;'";
                newLine += "onmouseover='this.style.color=\"orange\"'";
                newLine += "onmouseout='this.style.color=\"black\"'";
                newLine += "class='icon-remove-sign icon-large' title='订单作废' onclick='cancel(this," + orderId + ")'></i>";

                newLine += "</td></tr>";

                table.innerHTML += newLine;
            }

        }

        function detail(tableId, orderBeginDate, orderEndDate,waiterId,totalPrice,orderId) {

            var tid = document.getElementById("tableId");
            var obt = document.getElementById("orderBeginTime");
            var oet = document.getElementById("orderEndTime");
            var wid = document.getElementById("userAccount");
            var sp = document.getElementById("sumPrice");
            tid.innerHTML = tableId;
            obt.innerHTML = orderBeginDate;
            oet.innerHTML = orderEndDate;
            wid.innerHTML = waiterId;
            sp.innerHTML = totalPrice;

            xmlAjaxRequest("toorderdetail.order?orderId=" + orderId + "&time="
                + Math.random(), "get", null, true, detailList, null, null);
        }

        function detailList(responseXml, obj) {
            var table = document.getElementById("detailTable");
            table.innerHTML = "";
            var orderdishes = responseXml.getElementsByTagName("orderDetail");
            for (var i = 0; i < orderdishes.length; i++) {
                var orderdishe = orderdishes[i];
                var attrs = orderdishe.childNodes;
                var dishesName;
                var dishesPrice;
                var num;
                for (var j = 0; j < attrs.length; j++) {
                    var attr = attrs[j];
                    if (attr.nodeName == "dishesName") {
                        dishesName = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "dishesPrice") {
                        dishesPrice = attr.childNodes[0].nodeValue;
                    }
                    if (attr.nodeName == "num") {
                        num = attr.childNodes[0].nodeValue;
                    }
                }
//
                var newLine = "<tr><td>" + dishesName + "</td><td>" + dishesPrice
                    + "</td><td>" + num + "</td></tr>";

                table.innerHTML += newLine;

                $("#myModal").modal("show");
            }
        }

//        function showOrder(responseTxt, obj) {
//
//            if (responseTxt != "Org_EricYang_Platform_ErrorMsg:ServerPush_Wait_TimeOut") {
//
//                //--------------------------------
//
//                var responseXml = createXmlDom(responseTxt);
//                var order = responseXml.getElementsByTagName("order");
//
//                var attrs = order[0].childNodes;
//                var orderId;
//                var orderBeginDate;
//                var orderEndDate;
//                var waiterId;
//                var orderstate
//                var tableId;
//                var sumPrice;
//
//                for (var j = 0; j < attrs.length; j++) {
//                    var attr = attrs[j];
//                    if (attr.nodeName == "orderId") {
//                        orderId = attr.childNodes[0].nodeValue;
//                    }
//                    if (attr.nodeName == "orderBeginDate") {
//                        orderBeginDate = attr.childNodes[0].nodeValue;
//                    }
//                    if (attr.nodeName == "orderEndDate") {
//                        orderEndDate = attr.childNodes[0].nodeValue;
//                    }
//                    if (attr.nodeName == "waiterId") {
//                        waiterId = attr.childNodes[0].nodeValue;
//                    }
//                    if (attr.nodeName == "orderstate") {
//                        orderstate = attr.childNodes[0].nodeValue;
//                    }
//                    if (attr.nodeName == "tableId") {
//                        tableId = attr.childNodes[0].nodeValue;
//                    }
//                }
//
//                var table = document.getElementById("orderTable");
//                table.innerHTML = "";
//                var newLine = "<tr><td>" + tableId + "</td><td>" + orderBeginDate + "</td><td>" + orderEndDate + "</td>";
//                newLine += "<td>";
//                newLine += "<i style='cursor: pointer; font-size: 14;'";
//                newLine += "onmouseover='this.style.color=\"orange\"'";
//                newLine += "onmouseout='this.style.color=\"black\"'";
//                newLine += "class='icon-credit-card icon-large' title='确认结账' onclick='pay(this,"
//                    + orderId + ")'></i>&nbsp;&nbsp;";
//
//                newLine += "<i style='cursor: pointer; font-size: 14;'";
//                newLine += "onmouseover='this.style.color=\"orange\"'";
//                newLine += "onmouseout='this.style.color=\"black\"'";
//                newLine += "class='icon-sitemap icon-large' onclick='getOrderDetail("
//                    + orderId + ")' title='查看订单详情'></i>&nbsp;&nbsp;";
//
//                newLine += "<i style='cursor: pointer; font-size: 14;'";
//                newLine += "onmouseover='this.style.color=\"orange\"'";
//                newLine += "onmouseout='this.style.color=\"black\"'";
//                newLine += "class='icon-remove-sign icon-large' title='订单作废' onclick='cancel(this,"
//                    + orderId + ")'></i>";
//
//                newLine += "</td></tr>";
//
//                table.innerHTML += newLine;
//
//                //--------------------------------
//
//            }
//
//            txtAjaxRequest("getrtpay.order?messageTitle=rtpay&time="
//                + Math.random(), "get", true, null, showOrder, null, null);
//        }
    </script>
</head>

<body style="font-family: 微软雅黑" onload="begin()">
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
                        <li><a href="tomodifymypass.order?userId=${USER_INFO.userId
                        }">修改我的密码</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="toonlinekitchen.order">查看当前在线的厨师 </a></li>
                        <li><a href="toonlinewaiters.order">查看当前在线的点餐员</a></li>
                    </ul>
                </li>
                <li><a href="logout.order">退出登录</a></li>
            </ul>
            <form class="navbar-form navbar-right" method="post"
                  action="sendbord.order" target="formtarget">
                <input type="text" class="form-control" placeholder="公告"
                       style="width:300px" name="bord"><input
                    class="btn btn-default" type="submit" value="发送" />
                <iframe name="formtarget" width="0" height="0" style="display: none"></iframe>
            </form>
        </div>
    </div>
</nav>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="nav-header"
                    style="font-size: 18;margin-bottom: 10px;margin-left: 10px"><i
                        class="icon-credit-card icon-large"></i>&nbsp;运营功能
                </li>
                <li class="active"><a href="toadminmain.order"><i
                        class="icon-money icon-large"></i>&nbsp; 顾客结账界面 <span
                        class="sr-only">(current)</span></a></li>


            </ul>
            <ul class="nav nav-sidebar">
                <li class="nav-header"
                    style="font-size: 18;margin-bottom: 10px;margin-left: 10px"><i
                        class="icon-cog icon-large"></i>&nbsp;餐厅管理
                </li>
                <li><a href="touseradmin.order"><i
                        class="icon-group icon-large"></i>&nbsp;员工管理</a></li>
                <li><a href="todishesadmin.order"><i
                        class="icon-coffee icon-large"></i>&nbsp;菜品管理</a></li>
                <li><a href="tooperatedata.order"><i
                        class="icon-bar-chart icon-large"></i>&nbsp;查看经营数据 </a></li>

            </ul>
        </div>


        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <ol class="breadcrumb">
                <li><a href="/OrderSys/">首页</a></li>
                <li>管理员</li>
                <li class="active">管理管理界面</li>
            </ol>


            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h2 class="panel-title">今日特色推荐菜品</h2>
                </div>
                <div class="panel-body" style="padding-bottom: 0px">
                    <div class="row placeholders" id="dishesbord">
                    </div>

                    <nav>
                        <ul class="pager" style="margin-right: 20px">

                            <li class="next"><a href="todishesadmin.order">更多特色菜品 <span
                                    aria-hidden="true">&rarr;</span></a></li>
                        </ul>
                    </nav>
                </div>
            </div>


            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">待结账餐桌信息</h3>
                </div>
                <div class="panel-body">

                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>桌号</th>
                                <th>开餐时间</th>
                                <th>结账时间</th>
                                <th>总价</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="orderTable">

                            </tbody>
                        </table>
                        <!--
                        <nav>
                        <ul class="pager">
                            <li><a href="#">上一页</a></li>
                            <li><a href="#">下一页</a></li>
                        </ul>
                        </nav>
                         -->
                    </div>

                </div>
            </div>

            <div
                    style="height:1px;width: 100%;background: #CCC;margin-bottom: 10px"></div>
            <footer>
                <p>&copy; ${ORDER_SYS_NAME}-中软国际ETC 2015</p>
            </footer>

        </div>
    </div>
</div>

<br>



<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index: 2000">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">订单详情</h4>
            </div>
            <div class="modal-body"
                 style="background-image: url('img/body-bg.png')">
                <div class="container-fluid" style="padding: 0px 0px;">
                    <div class="row-fluid">
                        <div class="span6" style="width:30%;float:left">
                            <div
                                    style="background-color: #66A;padding: 15px;margin: 1px;height:202px;color:white;">
                                <h2 style="color: white;font-weight: bold;font-family: 微软雅黑">桌号</h2>
                                <p id="tableId"></p>

                                <p style="text-align: center;font-size: 24;margin-top: 11px ">
                                    <i class="icon-warning-sign icon-large"></i>
                                </p>
                            </div>
                        </div>
                        <div class="span6" style="width:70%;float:left">
                            <div class="row-fluid">
                                <div class="span6" style="width:50%;float:left">
                                    <div
                                            style="background-color: #CCC;padding: 15px;margin: 1px;height:100px;">
                                        <h3>开始时间</h3>
                                        <p id="orderBeginTime"></p>

                                    </div>

                                </div>
                                <div class="span6" style="width:50%;float:left">

                                    <div
                                            style="background-color: #CF0;padding: 15px;margin: 1px;height:100px;">
                                        <h3>结餐时间</h3>
                                        <p id="orderEndTime"></p>

                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span6" style="width:50%;float:left">

                                    <div
                                            style="background-color: #FA0;padding: 15px;margin: 1px;height:100px;">
                                        <h3>点餐服务员</h3>
                                        <p id="userAccount"></p>

                                    </div>
                                </div>
                                <div class="span6" style="width:50%;float:left">

                                    <div
                                            style="background-color: #DAD;padding: 15px;margin: 1px;height:100px;">
                                        <h3>总价（元）</h3>
                                        <p>
                                            <span style="color:red;font-weight: bold" id="sumPrice"></span>
                                        </p>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="panel panel-danger" style="margin-top: 10px">
                    <div class="panel-heading">
                        <h3 class="panel-title">订单详情</h3>
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped">

                            <caption>该桌的订单详情如下</caption>
                            <thead>
                            <tr>
                                <th>菜品</th>
                                <th>单价</th>
                                <th>数量</th>
                            </tr>
                            </thead>
                            <tbody id="detailTable">


                            </tbody>

                        </table>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>

            </div>
        </div>
    </div>
</div>


</body>
</html>