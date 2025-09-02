$(document).ready(function () {
    loadPosts();
});

function loadPosts() {
    $.ajax({
        url: "http://localhost:8080/api/lost/getall",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        success: function (response) {
            if (response.status === 200 && response.data) {
                const postsContainer = $("#postsContainer");
                postsContainer.empty();

                response.data.forEach(post => {
                    // Limit description to 5 words
                    let shortDescription = post.postDescription.split(" ").slice(0, 5).join(" ");
                    if (post.postDescription.split(" ").length > 5) {
                        shortDescription += "..."; // add ellipsis if longer
                    }
                    const postCard = `
                        <div class="bg-white rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300 cursor-pointer post-card"
                             data-info='${JSON.stringify(post)}'>
                            <img src="${post.photoUrl}" alt="${post.petType}" class="w-full h-56 object-cover">
                            <div class="p-6">
                                <h3 class="text-xl font-bold mb-2">${post.petType} - ${post.breed}</h3>
                                <p class="text-gray-700 mb-2">${shortDescription}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Color:</strong> ${post.color} | <strong>Gender:</strong> ${post.gender}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Location:</strong> ${post.city}, ${post.district}</p>
                                <p class="text-gray-400 text-xs">${post.postDate}</p>
                            </div>
                        </div>
                    `;
                    postsContainer.append(postCard);
                });

            } else {
                $("#postsContainer").html("<p class='text-center text-gray-500'>No posts found.</p>");
            }
        },
        error: function () {
            $("#postsContainer").html("<p class='text-center text-red-500'>Failed to load posts.</p>");
        }
    });
}


$("#filterForm").on("submit", function (e) {
    e.preventDefault();

    // Grab filter values
    const petType = document.getElementById("petTypeFilter").value;
    const status = document.getElementById("statusFilter").value;
    const district = document.getElementById("districtFilter").value;
    const city = document.getElementById("cityFilter").value;


    let filterParams = {
        petType: petType,
        status: status,
        district: district,
        city: city
    }

    loadFilteredPosts(filterParams);

});

function loadFilteredPosts(filter) {
    $.ajax({
        url: "http://localhost:8080/api/lost/filterpost",
        method: "POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        contentType: "application/json",  // tell Spring it's JSON
        data: JSON.stringify(filter),
        success: function (response) {
            if (response.status === 200) {
                const postsContainer = $("#postsContainer");
                postsContainer.empty();

                response.data.forEach(post => {
                    // Limit description to 5 words
                    let shortDescription = post.postDescription.split(" ").slice(0, 5).join(" ");
                    if (post.postDescription.split(" ").length > 5) {
                        shortDescription += "..."; // add ellipsis if longer
                    }
                    console.log(post)
                    const postCard = `
                        <div class="bg-white rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300 cursor-pointer post-card"
                             data-info='${JSON.stringify(post)}'>
                            <img src="${post.photoUrl}" alt="${post.petType}" class="w-full h-56 object-cover">
                            <div class="p-6">
                                <h3 class="text-xl font-bold mb-2">${post.petType} - ${post.breed}</h3>
                                <p class="text-gray-700 mb-2">${shortDescription}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Color:</strong> ${post.color} | <strong>Gender:</strong> ${post.gender}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Location:</strong> ${post.city}, ${post.district}</p>
                                <p class="text-gray-400 text-xs">${post.postDate}</p>
                            </div>
                        </div>
                    `;
                    postsContainer.append(postCard);
                });
            }
        }
    })
}


// Show modal when card is clicked
$(document).on("click", ".post-card", function () {
    const post = $(this).data("info");

    $("#modalPhoto").attr("src", post.photoUrl);
    $("#modalTitle").text(`${post.petType} - ${post.breed}`);
    $("#modalDescription").text(post.postDescription);
    $("#modalExtra").html(`<strong>Color:</strong> ${post.color} | <strong>Gender:</strong> ${post.gender}`);
    $("#modalLocation").html(`<strong>Location:</strong> ${post.city}, ${post.district}`);
    $("#modalContact").html(`<strong>Finder:</strong> ${post.finderName} | <strong>Contact:</strong> ${post.contactNumber}`);
    $("#modalLandmark").html(`<strong>Landmark:</strong> ${post.address}`);
    $("#modalStatus").html(`<strong>Status:</strong> ${post.status}`);
    $("#modalUser").html(`<strong>User:</strong> ${post.user} | ${post.postDate}`);

    $("#postModal").removeClass("hidden"); // show modal
});

// Close modal
$("#closeModal, #postModal").on("click", function (e) {
    if (e.target.id === "postModal" || e.target.id === "closeModal") {
        $("#postModal").addClass("hidden");
    }
});

