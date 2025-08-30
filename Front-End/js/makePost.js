
$("form").on("submit", (e) => {
    e.preventDefault();

    // Collect your dto object
    let dto = {
        postDescription: $("#postDescription").val(),
        petType: $("#petType").val(),
        breed: $("#breed").val(),
        color: $("#color").val(),
        gender: $("#gender").val(),
        district: $("#district").val(),
        city: $("#city").val(),
        landmark: $("#landmark").val(),
        finderName: $("#finderName").val(),
        contactNumber: $("#contactNumber").val(),
        postDate: new Date().toISOString(),
        status: "ACTIVE",
    };


})