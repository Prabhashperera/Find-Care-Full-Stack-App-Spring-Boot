let accessToken = localStorage.getItem("accessToken");

$(document).ready(function() {
    checkAndRefreshToken();
});

function checkAndRefreshToken() {
    if (accessToken != null) {
        $.ajax({
            url: "http://localhost:8080/api/auth/validate-token",
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("accessToken")
            },
            success: function(res) {
                console.log("Token is valid ✅", res);
            },
            error: function(err) {
                console.error("Token check failed ❌", err.responseJSON);
                console.log("VALIDATE TOKEN CALLED");
                generateNewAccessToken()
            }
        })
    }else {
        window.location.href = "../pages/signin.html";
    }
}

function generateNewAccessToken() {
    $.ajax({
        url: "http://localhost:8080/api/auth/refresh",
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: JSON.stringify({
            refreshToken: localStorage.getItem("refreshToken") // <-- use the refresh token
        }),
        success: function (data) {
            if (data.status == 200) {
                console.log("New Access Token:", data.data);
                localStorage.setItem("accessToken", data.data); // overwrite old access token
                window.location.reload();
            }
        },
        error: function(err) {
            console.error("Refresh failed", err);
            // optionally redirect to login
        }
    });
}


