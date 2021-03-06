/**
 * Created with IntelliJ IDEA.
 * User: gbougeard
 * Date: 01/03/13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
/*jshint globalstrict:true */
/*global angular:true */
'use strict';

angular.module('my_app.filters', [])
    .filter('interpolate', ['version', function(version) {
        return function(text) {
            return String(text).replace(/\%VERSION\%/mg, version);
        }
    }]);