/*$(document).on('keydown', function (e) {
     var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode == 122){//捕捉F11键盘动作
       e.preventDefault();  //阻止F11默认动作
       var el = document.documentElement;
       var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;//定义不同浏览器的全屏API
　　　　　　//执行全屏
       if (typeof rfs != "undefined" && rfs) {
             rfs.call(el);
       } else if(typeof window.ActiveXObject != "undefined"){
             var wscript = new ActiveXObject("WScript.Shell");
             if (wscript!=null) {
                 wscript.SendKeys("{F11}");
             }
       }
　　　 //监听不同浏览器的全屏事件，并件执行相应的代码
       document.addEventListener("webkitfullscreenchange", function() {//
           if (document.webkitIsFullScreen) {
                //全屏后要执行的代码
           }else{
                //退出全屏后执行的代码
       　　}
       }, false);

       document.addEventListener("fullscreenchange", function() {
           if (document.fullscreen) {
                //全屏后执行的代码
           }else{
                //退出全屏后要执行的代码
           }
       }, false);

       document.addEventListener("mozfullscreenchange", function() {
           if (document.mozFullScreen) {
                //全屏后要执行的代码
           }else{
                //退出全屏后要执行的代码
           }
       }, false);

       document.addEventListener("msfullscreenchange", function() {
           if (document.msFullscreenElement) {
                //全屏后要执行的代码
           }else{
                //退出全屏后要执行的代码
           }
       }, false);
    }
});*/

function fullScreen(after,exit){
  var el = document.documentElement;
  var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;//定义不同浏览器的全屏API
  if (typeof rfs != "undefined" && rfs) {
             rfs.call(el);
  } else if(typeof window.ActiveXObject != "undefined"){
       var wscript = new ActiveXObject("WScript.Shell");
       if (wscript!=null) {
           wscript.SendKeys("{F11}");
       }
  }


  document.addEventListener("webkitfullscreenchange", function() {//
     if (document.webkitIsFullScreen) {
          //全屏后要执行的代码
          if(after){
            after();
          }
     }else{
          //退出全屏后执行的代码
          if(exit){
            exit();
          }
  　　}
  }, false);

  document.addEventListener("fullscreenchange", function() {
     if (document.fullscreen) {
          //全屏后要执行的代码
          if(after){
            after();
          }
     }else{
          //退出全屏后执行的代码
          if(exit){
            exit();
          }
     }
  }, false);

  document.addEventListener("mozfullscreenchange", function() {
     if (document.mozFullScreen) {
          //全屏后要执行的代码
          if(after){
            after();
          }
     }else{
          //退出全屏后执行的代码
          if(exit){
            exit();
          }
     }
  }, false);

  document.addEventListener("msfullscreenchange", function() {
     if (document.msFullscreenElement) {
          //全屏后要执行的代码
          if(after){
            after();
          }
     }else{
          //退出全屏后执行的代码
          if(exit){
            exit();
          }
     }
  }, false);
}