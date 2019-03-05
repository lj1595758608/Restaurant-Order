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
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/ajax.js"></script>]
    <script type="text/javascript" src="bootstrap/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="json/json2.js"></script>



    <style type="text/css">
        #gongao{ont-weight:bold;}
        #gongao #scroll_begin, #gongao #scroll_end{display:inline}
    </style>
    <script type="text/javascript">

        function ScrollImgLeft(){
            var speed=50;
            var scroll_begin = document.getElementById("scroll_begin");
            var scroll_end = document.getElementById("scroll_end");
            var scroll_div = document.getElementById("scroll_div");
            scroll_end.innerHTML=scroll_begin.innerHTML;
            function Marquee(){
                if(scroll_end.offsetWidth-scroll_div.scrollLeft<=0)
                    scroll_div.scrollLeft-=scroll_begin.offsetWidth;
                else
                    scroll_div.scrollLeft++;
            }
            var MyMar=setInterval(Marquee,speed);
            scroll_div.onmouseover=function() {clearInterval(MyMar);}
            scroll_div.onmouseout=function() {MyMar=setInterval(Marquee,speed);}
        }

        var tableId;
        function getTableID() {
            var tabid =  document.getElementById("settableid").value;
            tableId = tabid;
            if(isNaN(tabid)){
                alert("请输入正确的桌号")
            } else {
                document.getElementById("tableid").innerHTML = "点单界面：当前桌号【"+ tabid +"】";
            }
        }

        var dishes = new Array();
        function SubmitOrder(obj,dishesId) {
            alert("添加菜品成功！");
            var num = $(obj).prev().find("input").val();
            var dishesDetail = new Array(dishesId,num);
            dishes.push(dishesDetail);
        }
        function SubmitAllOrder() {
            alert("提交订单成功，用餐愉快！");
//            var arrString=JSON.stringify(dishes);
//            $.cookie("dishes",arrString);
            xmlAjaxRequest("submitdishes.order?dishes=" + dishes + "&tableId="+ tableId + "&waiterId=" + ${USER_INFO.userId} + "&time="
                + Math.random(), "get", true, null, showList, null, null);
            xmlAjaxRequest("sendordermsg2.order?tableId=" + tableId + "&time="
                + Math.random(), "get", true, null, null, null, null);

        }

        function getDishesList(page) {
            xmlAjaxRequest("getdishesbypage.order?page=" + page + "&time="
                + Math.random(), "get", true, null, showList, null, null);
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

        function showList(responseXml, obj) {

            var maxPage = responseXml.getElementsByTagName("maxPage");
            maxPage = maxPage[0].childNodes;
            //alert(maxPage[0].nodeValue);
            var link = document.getElementById("last");
            link.href = "javascript:getDishesList(" + maxPage[0].nodeValue + ")";

            var page = responseXml.getElementsByTagName("page");
            page = page[0].childNodes;
            link = document.getElementById("next");
            link.href = "javascript:getDishesList("
                + (parseInt(page[0].nodeValue) + 1) + ")";
            link = document.getElementById("pre");
            link.href = "javascript:getDishesList("
                + (parseInt(page[0].nodeValue) - 1) + ")";
            var div = document.getElementById("dishesdiv");
            div.innerHTML = "";
            var dishes = responseXml.getElementsByTagName("dish");
            for ( i = 0; i < dishes.length; i++) {

                var dish = dishes[i];
                var attrs = dish.childNodes;
                var dishesId;
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


                var subDiscript = dishesDiscript.length > 8 ? dishesDiscript.substring(0, 11) + "..." : dishesDiscript;
                var newLine ="<div class='col-xs-6 col-sm-2 placeholder'>";
                newLine +="<center>";
                newLine += "<img src=\"img/dishes/" + dishesImg + "\" width=\"200px\" class=\"img-thumbnail\" style='border-radius:20px;width: 200px;height:200px'>";
                newLine +="<h4><span class=\"glyphicon glyphicon-heart\" style=\"font-size: 15px;color: #ff2749\">\n";
                newLine += dishesName;
                newLine +="</span></h4>\n";
                newLine +="<span class=\"text-muted\">\n";
                newLine += subDiscript;
                newLine += "<br>";
                newLine +="</span>\n";
                newLine +="<div class=\"button-group\" role=\"group\" aria-label=\"...\" style=\"padding-bottom: 5px\">\n";

                newLine +="<button type=\"button\" class=\"btn btn-default jian\" >-</button>";
                newLine +="<input type=\"text\" value=\"0\" class=\"btn btn-default num\" style='width: 50px;height: 35px' />";
                newLine +="<button type=\"button\" class=\"btn btn-default add\">+</button>";
                newLine +="</div>\n";
                newLine +="<input type='submit' class='btn btn-danger cart' style='width: 130px' value='加入点餐车' onclick='SubmitOrder(this,"+ dishesId + ")'/>\n";
                newLine +="<center>";
                newLine +="</div>";

                div.innerHTML += newLine;
            }
            $(function(){
//                $(".cart").click(function () {
//                   var num = $(".cart").previousSibling.previousSibling.nodeValue;
//                   alert(num);
//                });


                $(".add").click(function() {
                    var n = $(this).prev().val();
                    var num = parseInt(n) + 1;
                    var tableid = document.getElementById("settableid");
                    if (tableid.value == 0){
                        alert("请先设定桌号");
                        return;
                    }
                    if(num == -1) {
                        return;
                    }
                    $(this).prev().val(num);
                });
                $(".jian").click(function() {
                    var n = $(this).next().val();
                    var num = parseInt(n) - 1;
                    var tableid = document.getElementById("settableid");
                    if (tableid.value == 0){
                        alert("请先设定桌号");
                        return;
                    }
                    if(num == -1) {

                        return;
                    }
                    $(this).next().val(num);
                });
            });
        }
    </script>
    <title>服务员点餐界面</title>
</head>
<body onload="getDishesList(1)">
<%--导航栏--%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <%--图标--%>
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
        <%--navbar-collapse--%>
        <div class="collapse navbar-collapse">
            <%--当前用户栏--%>
            <div id="navbar" class="navbar-right">
                <ul class="nav navbar-nav navbar-right">
                    <li><span class="navbar-brand">餐厅管理界面</span></li>
                    <li><span class="navbar-brand" id="tableid">点单界面：当前桌号【】</span></li>
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
                            <li><a href="tomodifymypassforwaiter.order?userId=${USER_INFO.userId}">修改我的密码</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="toonlinekitchen.order">查看当前在线的厨师 </a></li>
                            <li><a href="toonlinewaiters.order">查看当前在线的点餐员</a></li>
                        </ul>
                    </li>
                    <li><a href="logout.order">退出登录</a></li>
                </ul>
            </div>
            <div class="navbar-right">&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <%--更改导航栏桌号,需同订餐车的数据一同导入数据库--%>
            <div class="navbar-right">
                <form class="navbar-form navbar-right" method="post"
                      action="#" target="formtarget">
                    <input type="text" maxlength="3" class="form-control" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder="设定桌号" style="width:300px" name="bord" id="settableid">
                    <input class="btn btn-default" type="submit" value="确认"  onclick="getTableID()"/>
                    <iframe name="formtarget" width="0" height="0" style="display: none"></iframe>
                </form>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="nav-header" style="font-size: 17px;margin-bottom: 10px;margin-left: 10px"><i class="icon-credit-card icon-large"></i>&nbsp;服务员点餐功能</li>
                <li class="active"><a href="towaitermain.order"><i class="icon-group icon-large"></i>&nbsp;服务点餐界面</a></li>
                <li><a href="todishescheckout.order"><i class="icon-coffee icon-large"></i>&nbsp;餐桌结账</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="/OrderSys/">首页</a></li>
                <li>服务员</li>
                <li class="active">服务员点餐界面</li>
            </ol>
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">最新公告消息</h3>
                </div>
                <div class="panel-body">
                    <%--<span class="" style="color: #ff8b1e;font-size: 20px"></span>--%>
                    <i class=" icon-envelope icon-large" style="color:orange;"></i>
                    <span id="msg"></span>
                </div>
            </div>

            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">菜品清单</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <div id="dishesdiv">
                            </div>
                        </table>
                        <nav>
                            <ul class="pager">
                                <li><a href="javascript:getDishesList(1)" id="firstpage">&larr;首页</a></li>
                                <li><a href="#" id="pre">上一页</a></li>
                                <li><a href="#" id="next">下一页</a></li>
                                <li><a href="#" id="last">末页&rarr;</a></li>
                            </ul>
                        </nav>
                    </div>
                    <center>
                        <input type="submit" class="btn btn-danger" style="width: 350px" id="submitbtu" value="确认订单" onclick="SubmitAllOrder()"/>
                    </center>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
