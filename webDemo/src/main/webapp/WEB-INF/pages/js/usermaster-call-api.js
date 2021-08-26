//<![CDATA[
'use strict'
let WDXC0002_API = (function ($) {
    return {
        searchUser: formData => DXCUtils.callAPI("/demo/usermaster","POST",formData),
        saveUser: formData => DXCUtils.callAPI("/demo/usermaster", "PUT",formData),
        deleteUser: formData =>  DXCUtils.callAPI("/demo/usermaster", "DELETE",formData),
        getFileData: fileId =>  DXCUtils.callAPI("/demo/usermaster/getFileData", "GET",fileId),
    }
}(jQuery));
//]]'>