const accessToken = localStorage.getItem("accessToken"); // if you want auth

if (!accessToken) {
    // No token at all
    window.location.href = "../pages/signin.html";
} else {
    $.ajax({
        url: "http://localhost:8080/api/auth/validate-token",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + accessToken
        },
        success: function(data) {
            console.log("Token is valid:", data);
        },
        error: function(xhr, status, error) {
            console.error("Validation error:", error);
            localStorage.removeItem("accessToken"); // optional cleanup
            window.location.href = "../pages/signin.html";
        }
    });
}