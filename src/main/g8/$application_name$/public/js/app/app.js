/**
 * Created with IntelliJ IDEA.
 * User: gbougeard
 * Date: 17/11/12
 * Time: 01:17
 * To change this template use File | Settings | File Templates.
 */
'use strict';

var $application_name$ = angular.module('$application_name$',
    [
        'ui',
        '$application_name$.controllers',
        '$application_name$.filters',
        '$application_name$.services',
        '$application_name$.directives'
    ]);

$application_name$.config(function ($routeProvider) {
    $routeProvider.
        when('/wiz1', {
            controller: 'MyController',
            templateUrl: '/fragments/frag.html'
        }).
        //when('/new', {controller:CreateCtrl, templateUrl:'detail.html'}).
        otherwise({redirectTo: '/'});
});

$application_name$.value('ui.config', {
    tinymce: {
//       theme: 'simple'
       theme: 'advanced'
//        mode : "textareas",
//        plugins : "autolink,lists,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",

    }
});









