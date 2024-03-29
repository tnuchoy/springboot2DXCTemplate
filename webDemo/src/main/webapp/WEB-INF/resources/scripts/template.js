//<![CDATA[
$(document).ready(function () {
    'use strict'
    // browser detection
    detectBrowser();
    if (!(_browser.chrome || (_browser.msie && _browser.version >= 11) || _browser.safari)) {
        window.location.replace("/demo/browserdoesnotsupport");
    }
    // end browser detection
    $.each($('form'), function (indexInArray, valueOfElement) {
        $(valueOfElement).on('submit', function (event) {
            event.preventDefault();
            return false;
        });
    });
    $('.ui.dimmer').dimmer({
        closable: false
    });
    $('body').on('click', '#menucaller', function () {
        $('.ui.sidebar')
            .sidebar('setting', 'transition', 'push')
            .sidebar('toggle');
    });
    // required for set active menu
    DXCUtils.setActiveMenu($('#currentScreenId').val());
    // end required for set active menu
    // menu and button authorization
    DXCUtils.authorizationRender($('#currentScreenId').val());
    // end menu and button authorization

    // timer
    var displayDBServerTime = async function () {
        let dbTime = await DXCUtils.callAPI("/demo/common/dbservertime", "GET");
        let dateTime = new Date(dbTime);
        $('.time').text(moment(dateTime).format('DD MMM YYYY HH:mm:ss'));
        setInterval(function () {
            dateTime.setTime((dateTime.getTime() + 1000));
            $('.time').text(moment(dateTime).format('DD MMM YYYY HH:mm:ss'));
        }, 1000);
    };
    // call displayDBServerTime immediately
    displayDBServerTime();
    // repeat call every 5 min to for update
    setInterval(displayDBServerTime, 300000);

    $.fn.form.settings.rules.space = function (value, inputvalue) {
        console.log(inputvalue);
        console.log(value);
        let pattern = /^$|^[^\s]+(\s+[^\s]+)*$/;
        let result = pattern.test(value);
        console.log(result);
        return result;
    };
});
//]]>