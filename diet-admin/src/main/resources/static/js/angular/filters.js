angular.module('admin.filters', [])
    .filter('FormatString', function () {
        return function (input) {
            if (input && input.length > 20) {
                return input.substr(0, 20) + "…"
            }
            return input;
        };
    })
;