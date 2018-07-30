angular.module('admin.directives', [])
    .directive('formatCurrency', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attr, ctrl) {

                ctrl.$formatters.push(fixWithTwo);

                ctrl.$parsers.push(function (viewValue) {
                    return parseFloat(viewValue.replace(new RegExp(",", "g"), ''));
                });

                element.bind("change", function () {
                    if (ctrl.$invalid) return;
                    var formattedModel = format(ctrl.$modelValue);
                    if (formattedModel !== ctrl.$viewValue) {
                        element.val(formattedModel);
                    }
                });
            }
        };
    });