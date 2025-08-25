
$("form").on("submit" , (e) => {
    e.preventDefault()

    let payload = {
        username: $(".user_Name").val(),
        password: $(".user_Password").val()
    };

    $.ajax({
        url: "http://localhost:8080/api/auth/signup",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function(response) {
            console.log("Success:", response);
        },
        error: function(xhr) {
            if (xhr.status === 409) {
                let msg = JSON.parse(xhr.responseText).message;
                $(".username-error").removeClass("hidden").text(msg);
            } else {
                alert("Something went wrong: " + xhr.responseText);
            }
        }
    });
})

