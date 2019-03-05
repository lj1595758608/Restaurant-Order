<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/9
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <script type="text/javascript">
        function begin() {

            var input = document.getElementById("inputfile");
            if (input.value != "") {
                var preview = document.getElementById("dishes");
                preview.src = "img/loading.gif";
                imgform.submit();
                request();
            }

        }

        function request() {
            txtAjaxRequest(
                "state.order?messageTitle=upstate&time=" + Math.random(),
                "get", true, null, showPreview, null, null);
        }
        function showPreview(responseTxt, obj) {

            var preview = document.getElementById("dishes");
            preview.src = "img/dishes/" + responseTxt;
            var path = document.getElementById("dishesimgname");
            path.value = responseTxt;
        }
        var formstate = 0;
        function testState() {

            var btu = document.getElementById("addbtu");
            if ((formstate & 1) == 1) {
                btu.disabled = false;
            } else {
                btu.disabled = "disabled";
            }
        }



        function checkDishesName() {

            var name = document.getElementById("firstname");
            txtAjaxRequest("checkdishes.order?name=" + encodeURIComponent(name.value)
                + "&time=" + Math.random(), "get", true, null, dishesCallback,
                null, null);
        }
        function dishesCallback(responseTxt, obj) {
            var error = document.getElementById("usererror");
            if (responseTxt == "OK") {
                formstate = formstate | 1;
                error.innerHTML = "";
            } else {

                if (formstate & 1 != 0) {
                    formstate = formstate ^ 1;
                }
                error.innerHTML = "菜名已经被占用！";

            }
            testState();
        }
    </script>
</head>

<body style="font-family: 微软雅黑">
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


<div class="container">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="nav-header"
                    style="font-size: 18;margin-bottom: 10px;margin-left: 10px"><i
                        class="icon-credit-card icon-large"></i>&nbsp;运营功能</li>
                <li ><a href="toadminmain.order"><i
                        class="icon-money icon-large"></i>&nbsp; 顾客结账界面 <span
                        class="sr-only">(current)</span></a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li class="nav-header"
                    style="font-size: 18;margin-bottom: 10px;margin-left: 10px"><i class="icon-cog icon-large"></i>&nbsp;餐厅管理</li>
                <li><a href="touseradmin.order"><i
                        class="icon-group icon-large"></i>&nbsp;员工管理</a></li>
                <li class="active"><a href="todishesadmin.order"><i
                        class="icon-coffee icon-large"></i>&nbsp;菜品管理</a></li>
                <li><a href="tooperatedata.order"><i
                        class="icon-bar-chart icon-large"></i>&nbsp;查看经营数据 </a></li>
            </ul>
        </div>
        <ol class="breadcrumb" style="margin-top:15px; ">
            <li><a href="/OrderSys/">首页</a></li>
            <li>管理员</li>
            <li class="active">菜品管理界面</li>
        </ol>
        <div style="width: 60%;display: inline-block;">
            <div>
                <h2>请填写菜品信息：</h2>
                <p>请在下面的表单中填写新增加的菜品名称，价格等描述信息</p>
                <div class="panel panel-danger">
                    <div class="panel-heading"><i class="icon-warning-sign"></i>&nbsp;注意事项</div>
                    <div class="panel-body">请正确填写菜品描述信息，菜品名称不要超过20个字符，菜品
                        简介不要超过200个字符，菜品详细描述不要超过400个字符，菜品价格请输入正确的数字格式</div>
                </div>
                <p>
                <form class="form-horizontal" role="form" style="margin-top: 20px" method="post" action="adddishes.order">
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">名&nbsp;&nbsp;&nbsp;&nbsp;称:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="firstname"
                                   name="dishesName" placeholder="请输入菜品名称"
                                   onblur="checkDishesName()" maxlength="20">

                        </div>

                        <div style="color: red;float: right;" id="usererror"></div>
                    </div>

                    <div class="form-group">
                        <label for="dishesdiscript" class="col-sm-2 control-label">简&nbsp;&nbsp;&nbsp;&nbsp;介:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="dishesdiscript"
                                   name="dishesDiscript" placeholder="请输入少于100字的简介" maxlength="200">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dishestxt" class="col-sm-2 control-label">说&nbsp;&nbsp;&nbsp;&nbsp;明:</label>

                        <div class="col-sm-10">
                            <textarea class="form-control" name="dishesTxt" id="dishestxt" cols="30" rows="10" maxlength="400"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dishesprice" class="col-sm-2 control-label">价&nbsp;&nbsp;&nbsp;&nbsp;格:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="dishesprice"
                                   name="dishesPrice" placeholder="请输入菜品价格"
                                   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')">
                        </div>
                    </div>
                    <div class="form-group">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="checkbox"  id="recommend" name="recommend" value="1">
                        <label for="recommend" class="control-label">推荐菜品</label>
                    </div>

                    <input type="hidden" value="default.jpg" id="dishesimgname"
                           name="dishesimg" />

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" class="btn btn-danger" disabled="disabled"
                                   id="addbtu" value="添加菜品" />
                        </div>
                    </div>
                </form>
            </div>

        </div>
        <%--竖线--%>
        <div style="width: 5%;display: inline-block;padding-top: 0px;margin-top: 0px;vertical-align: top;padding-left: 20px">
            <div style="background-color:#CCC; width:1px;height: 670px;margin-top: 0px"></div>
        </div>
        <div style="width: 30%;display: inline-block;vertical-align: top;">
            <h2>设置图片：</h2>
            <p style="margin-bottom: 10px">菜品图片上传成功后将在下面直接预览。</p>
            <div style="text-align: center;">
                <img src="img/dishes/1.jpg" id="dishes" width="200px"
                     height="200px" class="img-circle"
                     style="border:1px solid #CCC;box-shadow:0 0 10px rgba(100, 100, 100, 1);" />
                <p style="margin-top: 15px">当前菜品图像预览</p>
                <p style="margin-top: 15px">
                    为菜品指定新的图片，请选择图片文件后，点击<span style="color: red;font-weight: bold;">上传图片</span>按钮
                </p>
                <div>
                    <form class="form-inline" role="form"
                          enctype="multipart/form-data" action="upimg.order?location=dishes" name="imgform"
                          target="submitform" method="post">

                    <%--<form class="form-inline" role="form"--%>
                          <%--enctype="multipart/form-data" action="upimg.order?location=dishes" name="imgform"--%>
                          <%--target="submitform" method="post">--%>

                        <div class="form-group">
                            <label class="sr-only" for="inputfile">文件输入</label> <input
                                type="file" id="inputfile" name="uploadFile">
                        </div>

                        <input type="button" class="btn btn-danger" value="上传图片"
                               onclick="begin()" />
                    </form>
                </div>
            </div>
        </div>
        <div style="height:1px;width: 100%;background: #CCC;margin-bottom: 10px"></div>
        <footer>
                <p>&copy; ${ORDER_SYS_NAME}-中软国际ETC 2015</p>
            </footer>
    </div>
</div>

<br>
</body>
</html>

