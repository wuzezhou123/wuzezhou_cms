<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投票</title>
</head>
<body>

	<form id="form1">
		<div class="form-group">
			<label for="title"> 投票标题： </label> <input id="title" type="text" class="form-control"
				name="title">
		</div>
		<div class="form-group" id="options">
			<label for="title"> 投票项目： <button class="btn btn-info" type="button" onclick="addOptions()">增加项目</button> </label>
			 <input  type="text" name="options" class="form-control" >
		</div>
		<div class="form-group">
		 <button class="btn btn-warning" type="button" onclick="publishVote()">发起投票</button>
		</div>
	</form>
	<script type="text/javascript">
	
	 //发起投票
	  function publishVote(){
		 $.post("/my/publishVote",$("#form1").serialize(),function(flag){
			 alert("发布成功");
			 location.href="/my";
		 })
	 }
	
	//增加选项
	 function addOptions(){
		 
		 $("#options").append("<input type='text' name='options' class='form-control' style='margin-top:5px'>");
		 
	 }
	
	</script>
</body>
</html>