
$("form").on("submit" , (e) => {
    e.preventDefault();

    let userData = {
        username : $(".user_Name").val(),
        password: $(".user_Password").val(),
    }

    $.ajax({
        url: "http://localhost:8080/api/auth/login",
        contentType: "application/json",
        data: JSON.stringify(userData),
        type: "POST",
        success: function (data) {
            console.log(data.data);
            localStorage.setItem("token", data.data);
        }

    })
})