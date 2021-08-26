//<![CDATA[
'use strict'
let WDXC0002 = (function ($) {
    return {
        saveUserData: async  formData => {
            let saveResp = await WDXC0002_API.saveUser(formData);
            if (S(saveResp.message).isEmpty() == false) {
                let modal = DXCUtils.alertModal(saveResp.message, null);
                modal.modal('show');
            } else {
                $('#maintainSection').fadeOut(600, function () {
                    $('#searchSection').fadeIn(600);
                });
                // set save message
                if (saveResp.rowCount > 0) {
                    let modal = DXCUtils.alertModal('[[#{MBX00005AINF}]]', null);
                    modal.modal('show');
                } else {
                    let modal = DXCUtils.alertModal('[[#{MBX00009AERR}]]', null);
                    modal.modal('show');
                }
                let formData = $('#searchForm').form('get values');
                WDXC0002.populateUserDatatable(formData);
            }
        },
        populateUserDatatable: async function (formData) {
            $.LoadingOverlay('show');
            const responseData = await WDXC0002_API.searchUser(formData);
            if ($.isEmptyObject(responseData.data) == false) {
                $("#WDXC0002Edit").show();
                $("#WDXC0002Delete").show();
            } else {
                $("#WDXC0002Edit").hide();
                $("#WDXC0002Delete").hide();
            }
            if (S(responseData.message).isEmpty() == false) {
                let modal = DXCUtils.alertModal(responseData.message, null);
                modal.modal('show');
                $('#searchResultSection').hide();
            } else {
                $('#searchResultSection').show();
            }
            console.log(JSON.stringify(responseData.data));
            // datatable
            let searchResultTable = $("#tableSearchResult").DataTable({
                "data": responseData.data,
                "destroy": true,
                "lengthChange": false,
                "buttons": [{
                    text: '<i class="green file excel outline icon">',
                    titleAttr: 'Export to Excel',
                    extend: 'excelHtml5',
                    title: 'User Table',
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5, 6, 7, 8]
                    }
                }, 'pageLength'],
                "order": [
                    [1, "asc"]
                ],
                "columns": [{
                        "data": "citizenId",
                        "searchable": false,
                        "orderable": false,
                        "className": "dt-body-center",
                        "render": function (data, type, row) {
                            return ('<input type="checkbox" name="chkKey" value="' + data + '"/>');
                        },
                        "title": '<a href="#" onclick="return WDXC0002.clearCheckBox()"><i class="large square outline icon"/></a>'
                    },
                    {
                        "data": "citizenId",
                        "orderable": true,
                        "searchable": true,
                        "className": "dt-body-center"
                    },
                    {
                        "data": "firstName",
                        "orderable": true,
                        "searchable": true,
                        "className": "dt-body-left"
                    },
                    {
                        "data": "lastName",
                        "orderable": true,
                        "searchable": true,
                        "className": "dt-body-left"
                    },
                    {
                        "data": "dateOfBirth",
                        "orderable": true,
                        "searchable": true,
                        "className": "dt-body-center",
                        "render": function (data, type, row) {
                            return DXCUtils.formatDate(data, "DD/MM/YYYY");
                        }
                    },
                    {
                        "data": "address",
                        "orderable": true,
                        "searchable": true,
                        "className": "dt-body-left"
                    },
                    {
                        "data": "pictureId",
                        "orderable": false,
                        "className": "dt-body-center",
                         "render": function (data, type, row,meta) {
                             return '<a href="#" onclick="return WDXC0002.viewPicture('+data+');">Link</a>';
                         }
                    },
                    {
                        "data": "modifiedBy",
                        "orderable": true,
                        "className": "dt-body-left"
                    },
                    {
                        "data": "modifiedDt",
                        "orderable": true,
                        "className": "dt-body-center",
                        "render": function (data, type, row) {
                            return DXCUtils.formatDate(data, "DD/MM/YYYY HH:mm:ss");
                        }
                    }
                ],
                "initComplete": function(settings, json) {
                    $.LoadingOverlay('hide');
                }
            });
            searchResultTable.buttons().container().appendTo($('div.eight.column:eq(0)', searchResultTable.table().container()));
        },
        clearCheckBox: function () {
            let searchResultTable = $("#tableSearchResult").DataTable();
            searchResultTable.$('[name="chkKey"]').prop('checked', false);
        },
        viewPicture: function (pictureId) {
            //const responseData = await WDXC0002_API.getFileData(pictureId);
            $('#uploadPic').attr('src',data);
            $('#uploadLink').attr('href',data);
            $('#uploadLink').attr('download',file.name);
            $('#uploadCard').show();
        }
    }
}(jQuery));

$(document).ready(async function () {
    $.fn.dataTable.moment('DD/MM/YYYY HH:mm:ss');
    $("#WDXC0002Edit").hide();
    $("#WDXC0002Delete").hide();
    let currentDateTime = moment().toDate();
    $("#dateOfBirthCal").calendar({
          type: 'date',
          formatter: {
              date: function (date, settings) {
                  return DXCUtils.formatDate(date, 'DD/MM/YYYY');
              }
          },
          parser: {
              date: function (text, settings) {
                  return DXCUtils.parseDate(text, 'DD/MM/YYYY');
              }
          },
          maxDate: currentDateTime,
          today: true,
          monthFirst: false
      });
    // button
    let WDXC0002SearchClick;
    $('#WDXC0002Search').on('click', _.debounce(function (event) {
        let searchResultTable = $("#tableSearchResult").DataTable();
        searchResultTable.state.clear();
        searchResultTable.destroy();
        if ($('#searchForm').form('validate form')) {
            let formData = $('#searchForm').form('get values');
            WDXC0002.populateUserDatatable(formData);
        }
        return false;
    }, 300, true));
    let WDXC0002SaveClick;
    $('#WDXC0002Save').on('click', _.debounce(function (event) {
        event.preventDefault();
        function approveCallback() {
            let formData = $('#maintainForm').form('get values');
            WDXC0002.saveUserData(formData);
        }
        if ($('#maintainForm').form('validate form')) {
            let modal = DXCUtils.comfirmModal('[[#{MSTD0006ACFM}]]', null, approveCallback);
            modal.modal('show');
        }
        return false;
    }, 300, true));
    let WDXC0002CancelClick;
    $('#WDXC0002Cancel').on('click', _.debounce(function (event) {
        event.preventDefault();
        let modal = DXCUtils.comfirmModal('[[#{MBX0000MACFM}]]', null,
            function () {
                $('#maintainSection').fadeOut(600, function () {
                    $('#searchSection').fadeIn(600);
                });
            });
        modal.modal('show');
    }, 300, true));
    let WDXC0002AddClick;
    $('#WDXC0002Add').on('click', _.debounce(function (event) {
        event.preventDefault();
        $('#searchSection').fadeOut(600, function () {
            $('#maintainSection').fadeIn(600);
        });
        $('#maintainForm').find('input[name="citizenId"]').parent().removeClass("readonly");
        $('#maintainForm').find('input[name="citizenId"]').prop("readonly", false);
        $('#maintainForm').form('reset');
        // set mode
        $('#maintainForm').form('set values', {
            mode: DXCUtils.MODE_ADD
        });

        return false;
    }, 300, true));
    let WDXC0002EditClick;
    $('#WDXC0002Edit').on('click', _.debounce(function (event) {
        event.preventDefault();
        // check only one GIM Header data row to be edited
        let searchResultTable = $('#tableSearchResult').DataTable();
        let countChecked = searchResultTable.rows().nodes().to$().find(':checked[name="chkKey"]').length;

        if (countChecked != 1) {
            let modal = DXCUtils.alertModal('[[#{MSTD1017AERR}]]', null);
            modal.modal('show');
        } else {
            let maintainSection = {};
            $.each(searchResultTable.rows().nodes(), function (index, value) {
                if ($(value).find('[name="chkKey"]').prop('checked') == true) {
                    maintainSection = searchResultTable.row(value).data();
                    return;
                }
            });
            maintainSection.mode = "edit";
            maintainSection.dateOfBirth = DXCUtils.formatDate(maintainSection.dateOfBirth, 'DD/MM/YYYY');
            $('#maintainForm').find('input[name="citizenId"]').parent().addClass("readonly");
            $('#maintainForm').find('input[name="citizenId"]').prop("readonly", true);
            $('#maintainForm').form('reset');
            $('#maintainForm').form('set values', maintainSection);
            $('#searchSection').fadeOut(600, function () {
                $('#maintainSection').fadeIn(600);
            });
        }

        return false;
    }, 300, true));
    let WDXC0002DeleteClick;
    $('#WDXC0002Delete').on('click', _.debounce(function (event) {
        event.preventDefault();
        // check At least one User data row to be deleted
        let searchResultTable = $('#tableSearchResult').DataTable();
        let countChecked = searchResultTable.rows().nodes().to$().find(':checked[name="chkKey"]').length;

        if (countChecked == 0) {
            let modal = DXCUtils.alertModal('[[#{MSTD1016AERR}]]', null);
            modal.modal('show');
        } else {
            let modal = DXCUtils.comfirmModal('[[#{MSTD0007ACFM}]]',
            function () {
                let deleteUserArr = [];
                $.each(searchResultTable.rows().nodes(), function (index, node) {
                    if ($(node).find('[name="chkKey"]').prop('checked') == true) {
                        deleteUserArr.push(searchResultTable.row(node).data());
                        return;
                    }
                });
                $.ajax({
                    "async": true,
                    "url": "/demo/usermaster",
                    "type": "DELETE",
                    "contentType": "application/json; charset=utf-8",
                    "data": JSON.stringify(deleteUserArr),
                    "cache": false
                }).done(function (responseData, textStatus, jqXHR) {
                    if (S(responseData.message).isEmpty() == false) {
                        let modal = DXCUtils.alertModal(responseData.message, null);
                        modal.modal('show');
                    } else {
                        // set delete message
                        if (responseData.rowCount > 0) {
                            let modal = DXCUtils.alertModal('[[#{MBX00004AINF}]]', null);
                            modal.modal('show');
                        } else {
                            let modal = DXCUtils.alertModal('[[#{MBX00009AERR}]]', null);
                            modal.modal('show');
                        }
                        WDXC0002.populateUserDatatable(true);
                    }
                });
            });
        }

        return false;
    }, 300, true));
    let WDXC0002ClearClick;
    $('#WDXC0002Clear').on('click', _.debounce(function (event) {
        event.preventDefault();
        $('#searchForm').form('reset');
        let datatable = $("#tableSearchResult").DataTable();
        datatable.clear().draw();
        $("#WDXC0002Edit").hide();
        $("#WDXC0002Delete").hide();
        $("#searchResultSection").hide();

        return false;
    }, 300, true));
    // end button

    // form validation
    $('#searchForm').form({
        fields: {
            searchCitizenId: {
                identifier: 'searchCitizenId',
                rules: [{
                        type: 'maxLength[13]'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    }
                ]
            },
            searchFirstName: {
                identifier: 'searchFirstName',
                rules: [{
                        type: 'maxLength[100]'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    }
                ]
            },
            searchLastName: {
                identifier: 'searchLastName',
                rules: [{
                        type: 'maxLength[100]'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    }
                ]
            }
        },
        inline: true,
        on: 'blur'
    });
    // modal validation
    $('#maintainForm').form({
        fields: {
            citizenId: {
                identifier: 'citizenId',
                rules: [{
                        type: 'empty'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]',
                        prompt: '{name} must be number 13 digits'
                    },
                    {
                        type: 'maxLength[13]'
                    }
                ]
            },
            gimDesc: {
                identifier: 'firstName',
                rules: [{
                        type: 'empty',
                    },
                    {
                        type: 'space[this.value]',
                        prompt: '{name} should not be only white space or start or end with white space'
                    },
                    {
                        type: 'maxLength[100]'
                    }
                ]
            },
            cdLength: {
                identifier: 'cdLength',
                rules: [{
                        type: 'empty'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    },
                    {
                        type: 'integer[1..500]',
                        prompt: '{name} must be number'
                    }
                ]
            },
            field1Label: {
                identifier: 'field1Label',
                rules: [{
                        type: 'empty'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    },
                    {
                        type: 'maxLength[50]'
                    }
                ]
            },
            field2Label: {
                identifier: 'field2Label',
                rules: [{
                        type: 'empty'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    },
                    {
                        type: 'maxLength[50]'
                    }
                ]
            },
            field3Label: {
                identifier: 'field3Label',
                rules: [{
                        type: 'empty'
                    },
                    {
                        type: 'regExp[/^$|^[^\s]+(\s+[^\s]+)*$/]'
                    },
                    {
                        type: 'maxLength[50]'
                    }
                ]
            },
            activeFlag: {
                identifier: 'activeFlag',
                rules: [{
                    type: 'not[Select]'
                }]
            }
        },
        inline: true,
        on: 'blur'
    });

});
//]]'>