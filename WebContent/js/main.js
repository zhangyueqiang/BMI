function commitData(){
	//获取身高体重
	var height=document.getElementById("height_num").value;
	var weight=document.getElementById("weight_num").value;
	//计算 bmi=体重kg/（身高*2）m
	console.log('height'+height);
	console.log('weight'+weight);
	var bmi=weight/((height/100)*(height/100));
	bmi=Math.round(bmi*100)/100;
	console.log(bmi);
	var weiMin=18.5*(height/100)*(height/100);
	weiMin=Math.round(weiMin*100)/100;
	var weiMax=23.9*(height/100)*(height/100);
	weiMax=Math.round(weiMax*100)/100;
	document.getElementById("bmi").innerHTML="您的BMI值："+bmi;
	document.getElementById("wei").innerHTML="您的理想体重："+weiMin+"~"+weiMax+"kg之间";
	if (bmi<=18.4) {
		alert("您目前偏瘦，少生孩子多种树");
	}else if (bmi<=23.9) {
		alert("很好继续把");
	}else if (bmi>27.9) {
		alert("过重了 减肥吧");
	}else{
		alert("快去种树吧");
	}
	
	var date=new Date();
	var dateTime=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	console.log(dateTime);

	//显示当前页面
	//提交后台
	
//	如果是get请求的话，可以将参数放到路径中，用&来进行拼接
	$.ajax({
		type:"post",
		url:"/BMI/BMIServlet?method=add_bmi",
		data:{
			height:height,
			weight:weight,
			bmi:bmi,
			dateTime:dateTime
		},	//传递参数
		async:false,		//异步请求
		timeout:5000,		//5s超时
		dataType:"json",
		success:function(msg){
			console.log(msg);

			if (msg!=null){
				var table2=document.getElementById("table2");
				Translate(msg);
			}

		},
		error:function(xhr,textState){
			alert('add error');
		}
		
	});
	
	$.ajax({
		type:"post",
		url:"/BMIServlet?method=start_bmi",
		async:false,
		timeout:5000,
		dateType:"json",
		success:function(data){
			/*alert(data);*/

			var table2=document.getElementById("table2");
			for (var i in  data) {
				Translate(data[i]);
			}

		},
		error:function(xhr,textState){
			alert('load data error');
		}
	});
	
	
}

function Translate(data){

	var tr1=document.createElement("tr");
	tr1.id=data.id;		//add the unique flag for the tag tr

	table2.appendChild(tr1);


	tr1.innerHTML="<td>"+data.id+"</td><td>"+data.date+"</td><td>"+data.height
	+"</td><td>"+data.weight+"</td><td>"+data.bmi+"</td><td><a href="#" onclick='deleteData("+data.id+")'>delete</a><td>";


}

function deleteData(id){
	$.ajax({
		type:"post",
		url:"/BMIServlet?method=delete_bmi",
		data:{id:id},
		async:false,
		timeout:5000,
		dateType:"json",
		success:function(data){
			

		},
		error:function(xhr,textState){
			alert('delete error');
		}
	});


}