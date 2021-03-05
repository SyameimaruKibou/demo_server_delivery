var senderReg="",receiverReg="";

//处理查询快递信息
function postQueryMsg(){
     
 $.ajax({
	 type:"get",
     url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressList/receivertel/eq/"+$("#phoneNumber").val(),
     dataType:"json",
     async:false,
     success:function(data){
    	var table=document.getElementById("queryTable");
    	var tableDiv=document.getElementById("queryResultTable");
    	tableDiv.style.display="block";
    	
 	    for(var i=0;i<data.length;i++){
 	                            var row=table.insertRow(table.rows.length);
 	                            var c1=row.insertCell(0);
 	                            c1.innerHTML=data[i].ID;
 	                            var c2=row.insertCell(1);
 	                            c2.innerHTML=data[i].note;
 	                            var c3=row.insertCell(2);
 	                            c3.innerHTML=data[i].receivername;
 	                            var c4=row.insertCell(3);
 	                            c4.innerHTML="<a href = \"showExpressInfo.html?id="+data[i].ID+"\">Go!</a>"  ;
 	                        }

     },
	 error:function(errorThrown){
	 console.log(errorThrown);

	 }
  });
 event.preventDefault()
}

//处理预约寄件信息
function postSendMsg(){
     var $senderName=$("#senderName").val();
     var $senderPhoneNumber=$("#senderPhoneNumber").val();
     var $senderRegCode=$("#senderRegCode").val();
     var $senderAddress=$('#senderAddress').val();
     var $receiverName=$("#receiverName").val();
     var $receiverPhoneNumber=$("#receiverPhoneNumber").val();
     var $receiverRegCode=$("#receiverRegCode").val();
     var $receiverAddress=$("#receiverAddress").val();
     
     
     var TemporaryExpress = {
    		 "sendername" : $("#senderName").val(),
    		 "sendertel" : $("#senderPhoneNumber").val(),
    		 "senderregcode" : senderReg,
    		 "senderaddr" : $("#senderAddress").val(),
    		 "receivername" : $("#receiverName").val(),
    		 "receivertel" : $("#receiverPhoneNumber").val(),
    		 "receiverregcode" : receiverReg,
    		 "receiveraddr" : $("#receiverAddress").val(),
    		 "note" : $("#note").val()
     };
     
     var TestData = {"sendername":"诸葛琴魔","receivername":"司马老贼","receiverregcode":"140000",
    		 "receiveraddr":"五道口","senderregcode":"130000","ORMID":0,
    		 "SN":0,"type":0,"sendertel":"1000","senderaddr":"王府井","receivertel":"135551"};
     
     
     
     if(senderReg==""){
    	 alert("请填写选择寄件人地址！");
         event.preventDefault();
         return false;
     }
     if(receiverReg==""){
    	 alert("请填写选择收件人地址！");
         event.preventDefault();
         return false;
     }
     
     $.ajax({
         type:"post",
         url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/newTempExpress",
         dataType:"json",
         contentType:"application/json",
         dataType:"text",
         data:JSON.stringify(TemporaryExpress),
         async:false,
         success:function(data){
             console.log(data);
             alert("预约成功！您的快递价格约为："+priceCulc()+" 元");
	     },
         error: function (errorThrown) {
        	console.log(errorThrown);
        }
     });
     event.preventDefault()
}	  

//初始化省市区三级联动,顺便初始化了slider
function initSelect(){
	//=========================以下初始化slider=====================
	
	$(function(){  
		  
		$('#packageWeight').bind('input propertychange', function() {  
			$('#result').html($(this).val()+" kg");  
		});  

	});
	
	//=========================以下处理收件人========================
	var DISTRICTS;
	var provinceCode = '',
	    cityCode = '',
	    districtCode = '';
	//筛选的地区
	var province_selector = $('#province_selector'),
	    city_selector = $('#city_selector'),
	    district_selector = $('#district_selector');
	var htm = '<option value="">请选择</option>';
	
	var provinceCode1 = '',
	    cityCode1 = '',
	    districtCode1 = '';
	//筛选的地区
	var province_selector1 = $('#province_selector1'),
	    city_selector1 = $('#city_selector1'),
	    district_selector1 = $('#district_selector1');
	var htm1 = '<option value="">请选择</option>';
	
	
	$.ajax({
	    type: 'GET',
	    url: 'resources/districts.json',
	    dataType: 'json'
	}).done(function (res) {
	    DISTRICTS = res;
	    htm = '<option value="">请选择</option>';
	    for(var key in DISTRICTS['100000']) {
	        htm += '<option value="'+key+'">'+DISTRICTS['100000'][key]+'</option>';
	        htm1 += '<option value="'+key+'">'+DISTRICTS['100000'][key]+'</option>';
	    }
	    province_selector.html(htm);
	    city_selector.html('<option value="">请选择</option>');
	    district_selector.html('<option value="">请选择</option>');
	    
	    province_selector1.html(htm);
	    city_selector1.html('<option value="">请选择</option>');
	    district_selector1.html('<option value="">请选择</option>');
	}).fail(function () {
	    console.log('获取地区json数据失败');
	});

	province_selector.change(function () {
	    provinceCode =province_selector.find('option:selected').val();
	    console.log(provinceCode);
	    receiverReg = "";
	    htm = '<option value="">请选择</option>';
	    for(var key in DISTRICTS[provinceCode]) {
	        htm += '<option value="'+key+'">'+DISTRICTS[provinceCode][key]+'</option>';
	    }
	    city_selector.html(htm);
	    district_selector.html('<option value="">请选择</option>');
	});
	city_selector.change(function () {
	    cityCode =city_selector.find('option:selected').val();
	    console.log(cityCode);
	    receiverReg = "";
	    htm = '<option value="">请选择</option>';
	    for(var key in DISTRICTS[cityCode]) {
	        htm += '<option value="'+key+'">'+DISTRICTS[cityCode][key]+'</option>';
	        district_selector.html(htm);
	    }
	});
	district_selector.change(function () {
	    districtCode =district_selector.find('option:selected').val();
	    console.log(districtCode);
	    receiverReg = districtCode;
	});
	//================以下处理寄件人===========================
	
	province_selector1.change(function () {
	    provinceCode1 =province_selector1.find('option:selected').val();
	    console.log(provinceCode1);
	    senderReg = "";
	    htm1 = '<option value="">请选择</option>';
	    for(var key in DISTRICTS[provinceCode1]) {
	        htm1 += '<option value="'+key+'">'+DISTRICTS[provinceCode1][key]+'</option>';
	    }
	    city_selector1.html(htm1);
	    district_selector1.html('<option value="">请选择</option>');
	});
	city_selector1.change(function () {
	    cityCode1 =city_selector1.find('option:selected').val();
	    console.log(cityCode1);
	    senderReg = "";
	    htm1 = '<option value="">请选择</option>';
	    for(var key in DISTRICTS[cityCode1]) {
	        htm1 += '<option value="'+key+'">'+DISTRICTS[cityCode1][key]+'</option>';
	        district_selector1.html(htm1);
	    }
	});
	district_selector1.change(function () {
	    districtCode1 =district_selector1.find('option:selected').val();
	    console.log(districtCode1);
	    senderReg = districtCode1;
	});
	
}

//计算包裹费用
function priceCulc(){
	var weight = parseFloat(document.getElementById('packageWeight').value);
	if(weight==0)
		return weight;
	if(senderReg.substr(0,4)==receiverReg.substr(0,4))
		return 3*(weight-0.5)+10;
	else if(senderReg.substr(0,2)==receiverReg.substr(0,2))
		return 3*(weight-0.5)+12;
	else
		return 3*(weight-0.5)+20;
	
}
