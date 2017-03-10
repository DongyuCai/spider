/**
 * 所有平台相关的指令
 */
angular.module('app.directives', []).
    directive('appVersion', ['version', function (version) {
        return function (scope, elm, attrs) {
            elm.text(version);
        };
    }]).directive('tableheightresize', ['$window', function ($window) {
        return {
            link: function (scope, elem, attrs) {
                scope.onResize = function () {
                    var header = document.getElementById('header');
                    if(header){
                        elem.windowHeight = $window.innerHeight - header.clientHeight*2;
                    }
                    var toolbar = document.getElementById('toolbar');
                    if(toolbar){
                        elem.windowHeight = elem.windowHeight - toolbar.clientHeight*2;
                    }
                    var searchbar = document.getElementById('searchbar');
                    if(searchbar){
                        elem.windowHeight = elem.windowHeight - searchbar.clientHeight*2;
                    }
                    elem.windowHeight = elem.windowHeight;
                    $(elem).height(elem.windowHeight);
                }
                scope.onResize();
                angular.element($window).bind('resize', function () {
                    scope.onResize();
                })
            }
        }
    }]);