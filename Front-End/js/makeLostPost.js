

$("form").on("submit", (e) => {
    e.preventDefault();
    let accessToken = localStorage.getItem("accessToken");
    let userName = localStorage.getItem("userName");

    // Collect your dto object
    let dto = {
        postDescription: $("#postDescription").val(),
        petType: $("#petType").val(),
        breed: $("#breed").val(),
        color: $("#color").val(),
        gender: $("#gender").val(),
        district: $("#district").val(),
        city: $("#city").val(),
        landmark: $("#address").val(),
        finderName: $("#finderName").val(),
        contactNumber: $("#contactNumber").val(),
        postDate: new Date().toISOString(),
        status: "ACTIVE",
        user: userName,
    };

    let formData = new FormData();
    formData.append("dto" , JSON.stringify(dto));
    formData.append("file" , $("#petImage")[0].files[0]);

    $.ajax({
        url: "http://localhost:8080/api/lost/save",
        type: "POST",
        headers: {
            "Authorization": `Bearer ${accessToken}`
        },
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function() {
            $("#loader").removeClass("hidden"); // show loader
        },
        success: function (data) {
            console.log(data.data);
        },
        error: function (xhr) {
            console.error(xhr);
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
            });
        },
        complete: function (xhr) {
            $("#loader").addClass("hidden");// hide loader
            Swal.fire({
                title: "Good job!",
                text: "Thank you for your Kindness!",
                icon: "success"
            });
            $("form")[0].reset(); // resets all form inputs instantly
            $("#petImage").val("");
        }
    });

})