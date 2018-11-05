UNILEVER = {
  init: function () {
       jQuery('#select-route,#dialog-select-route').auiSelect2({
            width: 200
       });
       jQuery("#add-route").click(function (e) {
            e.preventDefault();
            AJS.dialog2("#route-dialog").show({
                height:100
            });
       });
       jQuery("#add-cities").click(function (e) {
            e.preventDefault();
            AJS.dialog2("#city-dialog").show({
                height:100
            });
            UNILEVER.getAllRoutes();
       });
        jQuery(".aui-button-link").click(function (e) {
            e.preventDefault();
            AJS.dialog2("#route-dialog").hide();
            AJS.dialog2("#city-dialog").hide();
        });
        jQuery("#dialog-add-route").click(function (e) {
            e.preventDefault();
            UNILEVER.addRoute();
        });
        jQuery("#add-city-button").click(function (e) {
            e.preventDefault();
            UNILEVER.addCity();
        });
        jQuery("#select-route").on('change', function () {
            var routeID = jQuery(this).val();
            if(routeID!="-1"){
            UNILEVER.getAllCitiesForRoute(routeID);
            }
        });
        
       
  },
  getAllCitiesForRoute:function(routeID){
      jQuery.ajax({
            method: "GET",
            url: AJS.contextPath() + "/rest/unilever/1.0/configuration/route/"+routeID,
            success: function (result, status) {
                UNILEVER.appendCities(result);
            },
            statusCode: {
                500: function () {
                    JIRA.Messages.showErrorMsg("Some thing went wrong", {closeable: true, timeout: 60});
                }
            },
            error: function (e) {
                JIRA.Messages.showErrorMsg("Unable to get cities for given route", {closeable: true, timeout: 60});
            }
        });
  },
  appendCities: function(result){
      var options = '<option value="-1">None</option>';
        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
                var city = result[i];
                options += "<option value='" + city.cityID + "'>" + city.cityName + "</option>";
            }
        }
        jQuery('#select-city option').remove();
        jQuery('#select-city').append(options);
        jQuery('#select-city').select2("val","-1");
  },
  getAllRoutes: function(){
      jQuery.ajax({
            method: "GET",
            url: AJS.contextPath() + "/rest/unilever/1.0/configuration/routes",
            success: function (result, status) {
                UNILEVER.appendRoutes(result);
            },
            statusCode: {
                500: function () {
                    JIRA.Messages.showErrorMsg("Some thing went wrong", {closeable: true, timeout: 60});
                }
            },
            error: function (e) {
                JIRA.Messages.showErrorMsg("Unable to get routes", {closeable: true, timeout: 60});
            }, complete: function (jqXHR, textStatus) {
            }
        });
  },
  appendRoutes: function(result){
      var options = '<option value="-1">None</option>';
        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
                var route = result[i];
                options += "<option value='" + route.routID + "'>" + route.routName + "</option>";
            }
        }
        jQuery('#dialog-select-route option').remove();
        jQuery('#dialog-select-route').append(options);
        jQuery('#dialog-select-route').select2("val","-1");
  },
  addRoute: function(){
        var routeName = jQuery("#dialog-route-name").val();
        if(routeName!=""){
            jQuery.ajax({
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            url: AJS.contextPath() + "/rest/unilever/1.0/configuration/route",
            data:JSON.stringify({routName:routeName}),
            success: function (result, status) {
                JIRA.Messages.showSuccessMsg("Route added succssfully",{closeable:true,timeout:60});
            },
            statusCode: {
                500: function () {
                    JIRA.Messages.showErrorMsg("Some thing went wrong", {closeable: true, timeout: 60});
                }
            },
            error: function (e) {
                JIRA.Messages.showErrorMsg("Unable to add routs", {closeable: true, timeout: 60});
            }, complete: function (jqXHR, textStatus) {
            }
        });
        }else{
            JIRA.Messages.showErrorMsg("Enter route name", {closeable: true, timeout: 60});
        }
  },
  addCity: function(){
      var cityName = jQuery("#dialog-city-name").val();
      var routID = jQuery("#dialog-select-route").val();
        if(cityName!=""){
            jQuery.ajax({
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            url: AJS.contextPath() + "/rest/unilever/1.0/configuration/city",
            data:JSON.stringify({cityName:cityName,routeID:routID}),
            success: function (result, status) {
                JIRA.Messages.showSuccessMsg("Route added succssfully",{closeable:true,timeout:60});
            },
            statusCode: {
                500: function () {
                    JIRA.Messages.showErrorMsg("Some thing went wrong", {closeable: true, timeout: 60});
                }
            },
            error: function (e) {
                JIRA.Messages.showErrorMsg("Unable to add routs", {closeable: true, timeout: 60});
            }, complete: function (jqXHR, textStatus) {
            }
        });
        }else{
            JIRA.Messages.showErrorMsg("Enter route name", {closeable: true, timeout: 60});
        }
  }
};
jQuery(document).ready(function () {
    UNILEVER.init();
});