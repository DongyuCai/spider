/**
 * 定义需要加载的文件。
 * 使用 oclazyload 插件
 * 2015-10-28 by Dongwenzhao
 */
'use strict';

angular.module('MainApp')
    .config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
            modules: [{
                name: 'perfectscroll',
                files: [
                    'bower_components/slimscroll/jquery.slimscroll.min.js'

                ]
            }, {
                name: 'selectModel',
                files: [
                    'bower_components/selection-model/dist/selection-model.min.js'
                ]
            }, {
                name: 'TreeControl',
                files: [
                    'bower_components/angular-tree-control/angular-tree-control.js',
                    'bower_components/angular-tree-control/css/tree-control.css',
                    'bower_components/angular-tree-control/css/tree-control-attribute.css'
                ]
            }, {
                name: 'suggest',
                files: [
                    'lib/bootstrap-suggest-plugin-master/src/bootstrap-suggest-padago.js'
                ]
            }]
        });
    }]);



/**
 * w5c 验证插件
 * {
    required      : "该选项不能为空",
    maxlength     : "该选项输入值长度不能大于{maxlength}",
    minlength     : "该选项输入值长度不能小于{minlength}",
    email         : "输入邮件的格式不正确",
    repeat        : "两次输入不一致",
    pattern       : "该选项输入格式不正确",
    number        : "必须输入数字",
    w5cuniquecheck: "该输入值已经存在，请重新输入",
    url           : "输入URL格式不正确",
    max           : "该选项输入值不能大于{max}",
    min           : "该选项输入值不能小于{min}"

}
 *  全局配置 参数
 *  名称          默认值  作用
 *  blurTrig     false  光标移除元素后是否验证并显示错误提示信息
 *  showError    true   可以是bool和function，每个元素验证不通过后调用该方法显示错误信息，默认true，显示错误信息在元素的后面。
 *  removeError  true   可以是bool和function，每个元素验证通过后调用该方法移除错误信息，默认true，验证通过后在元素的后面移除错误信息。
 */
angular.module('MainApp').config(["w5cValidatorProvider", function(w5cValidatorProvider) {

}]);



/**
 * 定义各个设备的全局参数。
 * 2015-10-28 by Dongwenzhao
 */
(function() {
    angular
        .module('app.core', [])
        .constant('APP_MEDIAQUERY', {
            'desktopLG': 1200,
            'desktop': 992,
            'tablet': 768,
            'mobile': 480
        });
})();

(function() {
    'use strict';

    angular.module('app.settings', [])
        .run(['$rootScope',function settingsRun($rootScope) {

            // Global Settings
            // -----------------------------------
            $rootScope.app = {
                name: 'Angle',
                description: 'Angular Bootstrap Admin Template',
                year: ((new Date()).getFullYear()),
                layout: {
                    isFixed: true,
                    isCollapsed: false,
                    isBoxed: false,
                    isRTL: false,
                    horizontal: false,
                    isFloat: false,
                    asideHover: false,
                    theme: null
                },
                useFullLayout: false,
                hiddenFooter: false,
                asideToggled: false,
                viewAnimation: 'ng-fadeInUp'
            };

            // Setup the layout mode
            // $rootScope.app.layout.horizontal = ( $rootScope.$stateParams.layout === 'app-h') ;


        }]);


})();


// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) { //author: meizz

    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
    //var da = date.replace("年", "-").replace("月", "-").replace("日", "").replace(/-/g, "/").split(/\/|\:|\ /);
    //new Date(da[0],da[1]-1,da[2],da[3],da[4],da[5]).Format("yyyy-M-d");

}