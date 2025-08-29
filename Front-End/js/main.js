const accessToken = localStorage.getItem("accessToken");
if (!accessToken) {
    window.location.href = "../pages/signin.html";
}
