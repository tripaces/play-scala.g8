'use strict';

$application_name$.filter('stepStatusRow', function () {
    var STATUS = {
        "OK" : "success",
        "KO" : "error",
        "PENDING" : "warning"
    };

    return function (status) {
        return STATUS[status];
    };
});
