	// var API_DOMAIN = "http://121.43.61.76:8080/zjgesf-api";
	// var WEB_DOMAIN = "http://121.43.61.76";

	var API_DOMAIN = "http://localhost:8090";
	var WEB_DOMAIN = "http://127.0.0.1:13345";



	function urlParamUtil(url){
    	var arr = url.substring(url.lastIndexOf('?') + 1,url.length).split('&');
    	var param = new Object();
	    for(var i = 0; i < arr.length; i++){
	    	var words = arr[i].split("="); 
	        if(words.length > 1){
	        	param[words[0]] = words[1];
	        }
	    }
	    return param;
	}

	function dateFormat(input,module,short){
		if(input){
			var today = new Date();
			var ty = today.getFullYear();//年
			var tm = today.getMonth() + 1;//月
			var td = today.getDate();//日

			var time = new Date(input);
			var y = time.getFullYear();//年
			var m = time.getMonth() + 1;//月
			var d = time.getDate();//日
			var h = time.getHours();//时
			var mm = time.getMinutes();//分
			var s = time.getSeconds();//秒


			var runTimeSec = Math.floor((new Date().getTime()-input) / 1000);
			var runTime = "";
			if (runTimeSec < 60) {
				runTime = runTimeSec + "秒";
			} else {
				var runTimeMin = Math.floor(runTimeSec / 60);
				if (runTimeMin < 60) {
					runTimeSec = runTimeSec - runTimeMin * 60;
					runTime = runTimeMin + "分" + runTimeSec + "秒";
				} else {
					var runTimeHour = Math.floor(runTimeMin / 60);
					if (runTimeHour < 24) {
						runTimeMin = runTimeMin - runTimeHour * 60;
						runTimeSec = runTimeSec - ((runTimeHour * 60) + runTimeMin) * 60;
						runTime = runTimeHour + "小时" + runTimeMin + "分" + runTimeSec + "秒";
					} else {
						var runTimeDay = Math.floor(runTimeHour / 24);
						runTimeHour = runTimeHour-(runTimeDay * 24);
						runTimeMin = runTimeMin - ((runTimeDay * 24) + runTimeHour) * 60;
						runTimeSec = runTimeSec - ((((runTimeDay * 24) + runTimeHour) * 60) + runTimeMin) * 60;
						runTime = runTimeDay + "天" + runTimeHour + "小时" + runTimeMin + "分" + runTimeSec + "秒";
					}
				}
			}

			var timeStr = '';
				if(module === 0){
					//只显示距离时间
					timeStr = y+"-"+m+"-"+d+" "+h+":"+mm+":"+s;
				}else if(module === 1){
					//只显示距离时间
					timeStr = runTime+"前";
				}else if(module === 2){
					//显示月日时间和距离时间
					timeStr = m+"-"+d+" "+h+":"+mm+":"+s+" &nbsp;"+runTime+"前";	
				}else{
					timeStr = y+"-"+m+"-"+d+" "+h+":"+mm+":"+s+" &nbsp;"+runTime+"前";	
				}
				
			return timeStr;
		}else{
			return '';
		}
	}