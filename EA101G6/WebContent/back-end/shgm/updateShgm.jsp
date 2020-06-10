<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*" %>
    
<%
    	ShgmVO shgmvo = (ShgmVO) request.getAttribute("shgmvo");
%>
<%-- 
String imgsrc = (String)request.getAttribute("imgsrc");
byte[] imgsave = (byte[])request.getAttribute("imgsave");
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add_Shgame</title>
<style>
	table, td,tr{
		text-align:center;
		border: black 2px solid;
	}
</style>
</head>
<body>
	<ul>
		<c:if test="${not empty errormsgs}">
			<c:forEach var="errors" items="${errormsgs}">
				<li>${errors}</li>
			</c:forEach>
		</c:if>
	</ul>
<form method="post" action="<%=request.getContextPath() %>/back-end/shgm/shgm.do" enctype="multipart/form-data">
	<table>
			<tr>
				<th>市集商品欄位</th>
				<th>請輸入資料</th>
			</tr>
			<tr>
				<td>市集商品編號</td>
				<td><%= shgmvo.getShgmno()%><input type="hidden" name="shgmno" value="<%= shgmvo.getShgmno()%>"></td>
			</tr>
			<tr>
				<td>賣家會員編號</td>
				<td><input type="text" name="sellerno" size="15"
					value="<%= shgmvo.getSellerno()%>"/></td>
			</tr>
			<tr>
				<td>買家會員編號</td>
				<td><input type="text" name="buyerno" size="15"
					value="<%= shgmvo.getBuyerno()%>"/></td>
			</tr>
			<tr>
				<td>市集商品名稱</td>
				<td><input type="text" name="shgmname" size="15"
					value="<%= shgmvo.getShgmname()%>"/></td>
			</tr>
			<tr>
				<td>市集商品價錢</td>
				<td><input type="text" name="price" size="15"
					value="<%= shgmvo.getPrice()%>"/></td>
			</tr>
			<tr>
				<td>市集商品簡介</td>
				<td><textarea name="intro" cols="32" rows="10"><%= shgmvo.getIntro()%>
					</textarea></td>
			
			</tr>
			<tr>
				<td>市集商品圖片</td>
				<td><input type="file" name="imgfile" id="imgfile" onchange="document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])"/>
					<img id="blah" alt="your image" width="100" height="100" src="<%=request.getContextPath()%>/back-end/shgm/displayimg?shgmno=<%= shgmvo.getShgmno()%>"/>
					<input type="hidden" name="imghidden" value="<%= shgmvo.getImg()%>"/></td>
			</tr>
			<tr>
				<td>上架審核狀態</td>
				<td><select id="upcheck" name="upcheck">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.upcheck == i)? 'selected':'' }>${(i == 0)? "未審核": (i == 1)? "已審核": "審核未通過"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>上架時間</td>
				<td><input type="text" name="uptime" id="f_date1" size="15"/></td>
			</tr>
			<tr>
				<td>取貨方式</td>
				<td><input type="text" name="take" size="15"
					value="<%= shgmvo.getTake()%>"/></td>
			</tr>
			<tr>
				<td>取貨人姓名</td>
				<td><input type="text" name="takernm" size="15"
					value="<%= shgmvo.getTakernm()%>"/></td>
			</tr>
			<tr>
				<td>取貨人電話</td>
				<td><input type="text" name="takerph" size="15"
					value="<%= shgmvo.getTakerph()%>"/></td>
			</tr>
			<tr>
				<td>取貨地址</td>
				<td><input type="text" name="address" size="15"
					value="<%= shgmvo.getAddress()%>"/></td>
			</tr>
			<tr>
				<td>出貨狀態</td>
				<td><select id="boxstatus" name="boxstatus">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.boxstatus == i)? 'selected':'' }>${(i == 0)? "未出貨": (i == 1)? "已出貨": "送達"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>付款狀態</td>
				<td><select id="paystatus" name="paystatus">
						<c:forEach var="i" begin="0" end="1">
							<option value="${i}" ${(shgmvo.paystatus == i)? 'selected':'' }>${(i == 0)? "未付款": "已付款"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>訂單狀態</td>
				<td><select id="status" name="status">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.status == i)? 'selected':'' }>${(i == 0)? "未完成": (i == 1)? "已完成": "取消"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>售出時間</td>
				<td><input type="text" name="soldtime" id="f_date2" size="15"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="hidden" name="action" value="update">	
					<input type="submit" value="送出" ></td>
			</tr>
	</table>
</form>
	<a href="<%=request.getContextPath() %>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>

<% 
  java.sql.Timestamp uptime = null;
  try {
	  uptime = shgmvo.getUptime();
   } catch (Exception e) {
	   uptime = new java.sql.Timestamp(System.currentTimeMillis());
   }
  java.sql.Timestamp soldtime = null;
  try {
	  soldtime = shgmvo.getSoldtime();
   } catch (Exception e) {
	   soldtime = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
//document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])
$("#imgfile").click(function(){
  $("img").append("<input type=\"hidden\" name=\"clickcheck\" value=\"clicked\" >");
});
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y/m/d',         //format:'Y-m-d H:i:s',
		   value: '<%=uptime%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y/m/d',         //format:'Y-m-d H:i:s',
		   value: '<%=soldtime%>', // value:   new Date(),
        });
</script>
</html>