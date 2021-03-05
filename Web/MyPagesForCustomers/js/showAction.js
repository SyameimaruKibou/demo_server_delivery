var packageId = getQueryVariable("id");

// 百度地图API功能
var map = new BMap.Map("allmap");
var haveTaken = false;
map.enableScrollWheelZoom(true); 

$.ajax({
	 type:"get",
   url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressRoute/"+packageId,
   dataType:"json",
   async:false,
   success:function(data2){
   	//alert("Got data!");
   	showRoute(data2)
   },
	 error:function(errorThrown){
		 console.log(errorThrown);
	 }
});

$.ajax({
	 type:"get",
    url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressSheetHistory/"+packageId,
    dataType:"json",
    async:false,
    success:function(data){
    	//alert("Got data!");
    	haveGotData(data)
    },
	 error:function(errorThrown){
		 console.log(errorThrown);
	 }
 });

function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}

function showRoute(data2)
{
	
	var points2 = new Array();
	var startPoint2 = new BMap.Point(data2[0].y, data2[0].x);
	var endPoint2 = new BMap.Point(data2[data2.length-1].y, data2[data2.length-1].x);
	
	for(var i = 0; i<data2.length; i++){
		var tmpPoint2 = new BMap.Point(data2[i].y, data2[i].x);
	    points2.push(tmpPoint2);
	}
	

	var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: false}});
	driving.search(startPoint2, endPoint2,{waypoints:points2});
}

function haveGotData(data)
{
	var table=document.getElementById("historyTable"); 
	var points=new Array();
	var startPoint = new BMap.Point(data[0].sourcenode.y, data[0].sourcenode.x);
	var endPoint = new BMap.Point(116.404, 39.915);
	
    for(var i=data.length-1;i>=0;i--){
	    var row=table.insertRow(table.rows.length);
	    var c1=row.insertCell(0);
	    var curPoint;
	    c1.innerHTML=data[i].actTime;
	    
	    var result = "";
	    
		var tmpPoint = new BMap.Point(data[i].eps.currentnode.y, data[i].eps.currentnode.x);
	    points.push(tmpPoint);
	    
		switch(parseInt(data[i].actId)){
		case 0:
			result = "您的快递由 " + data[i].user.name + "(电话：" + 
				data[i].user.telCode + ")  在网点 " + data[i].sourcenode.nodeName + " 被揽收";
			break;
		case 1:
			result =  "您的快递由 " + data[i].user.name + "(电话：" + 
					data[i].user.telCode + ")  在 " + data[i].sourcenode.nodeName + " 被打包";
			break;
		case 3:
			result =  "您的快递由 " + data[i].user.name + "(电话：" + 
					data[i].user.telCode + ") 司机运输，离开 " + data[i].sourcenode.nodeName +
					" 前往下一站 " + data[i].targetnode.nodeName;
			break;
		case 4:
			result =  "您的快递由 " + data[i].user.name + "(电话：" + 
					data[i].user.telCode + ") 司机运输，抵达 " + data[i].targetnode.nodeName;
			break;
		case 5:
			result =  "您的快递已从 " + data[i].targetnode.nodeName + " 出发，派送员" + 
					data[i].user.name + "(电话：" + 
					data[i].user.telCode + ") 正在为您派送 ";
			break;
		case 6:
			result = "您的快递已由派送员 " + data[i].user.name + "(电话：" + 
					data[i].user.telCode + ")送达，已签收，感谢您的使用" ;
			break;
		default:
			result = "没有找到快递信息";
		}
	    
	    var c2=row.insertCell(1);
	    c2.innerHTML=result;
    }

    if(data[data.length-1].actId==6){
    	curPoint = new BMap.Point(data[data.length-1].eps.currentnode.y, data[data.length-1].eps.currentnode.x);
    	haveTaken = true;
    }
    else
	    $.ajax({
			 type:"get",
		    url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getEpsLoc/"+packageId,
		    dataType:"json",
		    async:false,
		    success:function(data){
		    curPoint = new BMap.Point(data.y,data.x);
		    },
			 error:function(errorThrown){
				 console.log(errorThrown);
			 }
		 });

	//添加标绘
	var marker = new BMap.Marker(curPoint);
	marker.setZIndex(999);
	map.addOverlay(marker);
	map.centerAndZoom(curPoint, 15);	
	
/*
	//创建起点图标
	myIcon = new BMap.Icon("resources/icon_st.png", new BMap.Size(300,157));
	marker = new BMap.Marker(curPoint,{icon:myIcon});  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中

	//创建终点图标
	myIcon = new BMap.Icon("resources/icon_en.png", new BMap.Size(300,157));
	marker = new BMap.Marker(curPoint,{icon:myIcon});  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	
	/*
	//创建折线
	var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
	map.addOverlay(polyline);
*/
	
	//var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	//driving.search(startPoint, curPoint,{waypoints:points});
	
	
}

function goRealTimeLoc(){
	if(haveTaken)
		alert("该快递已签收！");
	else
		window.location = "realTimeLoc.html?id="+ packageId;
}
