// Global ajax prefilter (injects access token everywhere)
$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    if (!options.url.includes("/login") && !options.url.includes("/register")) {
        options.headers = options.headers || {};
        options.headers["Authorization"] = "Bearer " + localStorage.getItem("accessToken");
    }
});

// Global error handler for 401
$(document).ajaxError(function (event, jqXHR, settings) {
    if (jqXHR.status === 401) {
        // Try refresh
        console.log("jqXHR.responseText");
        $.ajax({
            url: "/refresh-token",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ refreshToken: localStorage.getItem("refreshToken") })
        }).done(function (response) {
            // Save new token
            localStorage.setItem("accessToken", response.accessToken);
            // Retry original request
            settings.headers = settings.headers || {};
            settings.headers["Authorization"] = "Bearer " + response.accessToken;
            $.ajax(settings); // re-run
        }).fail(function () {
            alert("Session expired! Please log in again.");
            localStorage.clear();
            window.location.href = "/login.html";
        });
    }
});
