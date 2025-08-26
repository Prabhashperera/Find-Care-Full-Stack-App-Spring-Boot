const token = localStorage.getItem("token");

if (!token) {
    // No token at all
    window.location.href = "../pages/signin.html";
} else {
    $.ajax({
        url: "http://localhost:8080/api/auth/validate-token",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function(data) {
            console.log("Token is valid:", data);
            window.location.href = "../pages/homePage.html";
        },
        error: function(xhr, status, error) {
            console.error("Validation error:", error);
            localStorage.removeItem("token"); // optional cleanup
            window.location.href = "../pages/signin.html";
        }
    });
}
