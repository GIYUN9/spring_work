<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대기오염 정보</title>
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body onload="init()">
	<h1>실시간 대기 오염 정보</h1>
	
	지역 : 
	<select id="location">
		<option>서울</option>
		<option>대전</option>
		<option>대구</option>
	</select>
	
	<button id="btn1">해당 지역 대기 오염 정보</button>
	<br><br>
	
	<table>
		<thead>
			<tr>
				<th>측정소명</th>
				<th>측정 일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지</th>
				<th>아황산가스</th>
				<th>일산화탄소</th>
				<th>오존</th>
				<th>이산화질소</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
	
		const init = function(){
			const btn = document.getElementById("btn1");
			btn.onclick = function(){
				$.ajax({
					url: "air.do",
					data: {
						location: document.getElementById('location').value
					},
					success: function(data){
						console.log(data);
						drawFunk(data);
					},
					error: function(){
						console.log("air.do ajax 실패");
					}
				})
			}
		}
		
		const drawFunk = function(data) {
			const itemArr = data.response.body.items;
			
			let str = "";
			for (let i in itemArr){
				let item = itemArr[i]
				
				str += '<tr>'
					+	'<td>'+ item.stationName +'</td>'
					+	'<td>'+ item.dataTime +'</td>'
					+	'<td>'+ item.khaiValue +'</td>'
					+	'<td>'+ item.pm10Value +'</td>'
					+	'<td>'+ item.so2Value +'</td>'
					+	'<td>'+ item.coValue +'</td>'
					+	'<td>'+ item.o3Value +'</td>'
					+	'<td>'+ item.no2Value +'</td>'
					+	'</tr>';
			}
			
			document.querySelector("table > tbody").innerHTML = str;
		}
	</script>
</body>
</html>