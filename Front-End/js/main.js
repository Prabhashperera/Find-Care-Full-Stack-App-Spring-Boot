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


// const token = localStorage.getItem("token");
//
// $.ajax({
//     url: "http://localhost:8080/api/pets", // protected endpoint
//     type: "GET",
//     headers: {
//         "Authorization": "Bearer " + token // <-- this is key
//     },
//     success: function(data) {
//         console.log("Data:", data);
//     },
//     error: function(xhr) {
//         if (xhr.status === 401) {
//             // Token invalid/expired
//             localStorage.removeItem("token");
//             window.location.href = "../pages/signin.html";
//         } else {
//             console.error("Something went wrong:", xhr.responseText);
//         }
//     }
// });