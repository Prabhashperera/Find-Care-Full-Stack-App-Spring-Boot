$(document).ready(function () {
    loadPosts();
});

function loadPosts() {
    $.ajax({
        url: "http://localhost:8080/api/found/getall",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        success: function (response) {
            if (response.status === 200 && response.data) {
                const postsContainer = $("#postsContainer");
                postsContainer.empty();

                response.data.forEach(post => {
                    const postCard = `
                        <div class="bg-white rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300">
                            <img src="${post.photoUrl}" alt="${post.petType}" class="w-full h-56 object-cover">
                            <div class="p-6">
                                <h3 class="text-xl font-bold mb-2">${post.petType} - ${post.breed}</h3>
                                <p class="text-gray-700 mb-2">${post.postDescription}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Color:</strong> ${post.color} | <strong>Gender:</strong> ${post.gender}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Location:</strong> ${post.city}, ${post.district}</p>
                                <p class="text-gray-500 text-sm mb-2"><strong>Finder:</strong> ${post.finderName} | <strong>Contact:</strong> ${post.contactNumber}</p>
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

    loadPosts({petType, status, district, city});

});


function loadFilteredPosts(filter) {
    $.ajax({
        url: "http://localhost:8080/api/found/filterpost",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        data: filter,
        success: function (response) {
            if (response.status === 200) {
                console.log(response);
            }
        }
    })
}
