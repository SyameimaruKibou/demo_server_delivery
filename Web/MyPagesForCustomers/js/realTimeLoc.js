var packageId = getQueryVariable("id");
var i = 1;

var map = new BMap.Map("map-real");
var point = new BMap.Point(113.664982,34.747556);
var preLoc,curLoc;

map.centerAndZoom(point, 12);
map.enableScrollWheelZoom(true); 

$(function(){
	getCurLoc();
	setInterval("refreshMap()",10000);
})
function refreshMap(){
	i = i+1;
	$("#btn").html("已经刷新 <font color=\"blue\">"+i+"</font> 次");
	
	//获取之前快递位置
	preLocPt = curLoc.getPosition();

	//删除上一次获取的标绘
	map.removeOverlay(curLoc);
	
	//获取当前快递位置
	getCurLoc();
	
	//绘制前一位置到当前位置的标绘
	var polyline = new BMap.Polyline([
		preLocPt,
		curLoc.getPosition()
	], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});
	map.addOverlay(polyline);
	
}

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

function getCurLoc(){
	//获取包裹历史信息
	$.ajax({
		 type:"get",
	    url:"https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getEpsLoc/"+packageId,
	    dataType:"json",
	    async:false,
	    success:function(data){
	    	var pt;
    		//使用最后一条信息的x，y，获得坐标点
    		pt = new BMap.Point(data.y,data.x);
    		//添加标绘
    		var marker = new BMap.Marker(pt);
    		map.addOverlay(marker);
    		map.centerAndZoom(pt, 15);
    		//返回添加的标绘
    		curLoc = marker;    	
	    		
	    },
		 error:function(errorThrown){
			 console.log(errorThrown);
		 }
	 });
	
	
	
}