$(document).ready(function () {
    loadPosts();
    $(".user_Name_Profile").text(localStorage.getItem("userName"));
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
                    // Limit description to 5 words
                    let shortDescription = post.postDescription.split(" ").slice(0, 5).join(" ");
                    if (post.postDescription.split(" ").length > 5) {
                        shortDescription += "..."; // add ellipsis if longer
                    }

                    // Determine status styling
                    const statusClass = post.status === 'ACTIVE' ? 'bg-green-400 pulse-ring' : 'bg-blue-400';
                    const statusIcon = post.petType.toLowerCase() === 'dog' ? '🐕' : '🐱';

                    const postCard = `
                        <div class="pet-card rounded-3xl shadow-xl overflow-hidden cursor-pointer post-card"
                             data-info='${JSON.stringify(post)}'>
                            <div class="relative">
                                <img src="${post.photoUrl}" alt="${post.petType}" class="w-full h-56 object-cover">
                                <div class="absolute top-4 left-4">
                                    <span class="tag px-3 py-1 rounded-full text-sm font-medium">${statusIcon} ${post.petType}</span>
                                </div>
                                <div class="absolute top-4 right-4">
                                    <div class="w-3 h-3 ${statusClass} rounded-full"></div>
                                </div>
                            </div>
                            <div class="p-6">
                                <div class="flex items-start justify-between mb-3">
                                    <h3 class="text-xl font-bold text-gray-800">${post.breed}</h3>
                                    <span class="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full">${post.postDate}</span>
                                </div>
                                <p class="text-gray-600 mb-2 font-medium">${shortDescription}</p>
                                <p class="text-gray-500 text-sm mb-2">${post.color} • ${post.gender}</p>
                                <div class="flex items-center text-gray-500 text-sm mb-4">
                                    <i class="fas fa-map-marker-alt mr-2 text-indigo-500"></i>
                                    ${post.city}, ${post.district}
                                </div>
                                <button class="w-full bg-gradient-to-r from-indigo-500 to-purple-600 text-white px-4 py-3 rounded-2xl hover:shadow-lg hover:scale-105 transition-all duration-300 font-semibold">
                                    <i class="fas fa-eye mr-2"></i>View Details
                                </button>
                            </div>
                        </div>
                    `;
                    postsContainer.append(postCard);
                });

            } else {
                $("#postsContainer").html("<p class='text-center text-gray-500 col-span-full text-xl'>No posts found.</p>");
            }
        },
        error: function () {
            $("#postsContainer").html("<p class='text-center text-red-500 col-span-full text-xl'>Failed to load posts.</p>");
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
    console.log(filterParams);
    loadFilteredPosts(filterParams);
});

function loadFilteredPosts(filter) {
    $.ajax({
        url: "http://localhost:8080/api/found/filterpost",
        method: "POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        contentType: "application/json",
        data: JSON.stringify(filter),
        success: function (response) {
            if (response.status === 200) {
                const postsContainer = $("#postsContainer");
                postsContainer.empty();

                response.data.forEach(post => {
                    // Limit description to 5 words
                    let shortDescription = post.postDescription.split(" ").slice(0, 5).join(" ");
                    if (post.postDescription.split(" ").length > 5) {
                        shortDescription += "...";
                    }

                    // Determine status styling
                    const statusClass = post.status === 'ACTIVE' ? 'bg-green-400 pulse-ring' : 'bg-blue-400';
                    const statusIcon = post.petType.toLowerCase() === 'dog' ? '🐕' : '🐱';

                    const postCard = `
                        <div class="pet-card rounded-3xl shadow-xl overflow-hidden cursor-pointer post-card"
                             data-info='${JSON.stringify(post)}'>
                            <div class="relative">
                                <img src="${post.photoUrl}" alt="${post.petType}" class="w-full h-56 object-cover">
                                <div class="absolute top-4 left-4">
                                    <span class="tag px-3 py-1 rounded-full text-sm font-medium">${statusIcon} ${post.petType}</span>
                                </div>
                                <div class="absolute top-4 right-4">
                                    <div class="w-3 h-3 ${statusClass} rounded-full"></div>
                                </div>
                            </div>
                            <div class="p-6">
                                <div class="flex items-start justify-between mb-3">
                                    <h3 class="text-xl font-bold text-gray-800">${post.breed}</h3>
                                    <span class="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full">${post.postDate}</span>
                                </div>
                                <p class="text-gray-600 mb-2 font-medium">${shortDescription}</p>
                                <p class="text-gray-500 text-sm mb-2">${post.color} • ${post.gender}</p>
                                <div class="flex items-center text-gray-500 text-sm mb-4">
                                    <i class="fas fa-map-marker-alt mr-2 text-indigo-500"></i>
                                    ${post.city}, ${post.district}
                                </div>
                                <button class="w-full bg-gradient-to-r from-indigo-500 to-purple-600 text-white px-4 py-3 rounded-2xl hover:shadow-lg hover:scale-105 transition-all duration-300 font-semibold">
                                    <i class="fas fa-eye mr-2"></i>View Details
                                </button>
                            </div>
                        </div>
                    `;
                    postsContainer.append(postCard);
                });

                if (response.data.length === 0) {
                    $("#postsContainer").html("<p class='text-center text-gray-500 col-span-full text-xl'>No pets found matching your filters.</p>");
                }
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
    $("#modalTag").text(`${post.petType.toLowerCase() === 'dog' ? '🐕' : '🐱'} ${post.petType}`);

    // Update modal content with modern styling
    const modalContent = `
        <div class="flex items-center">
            <i class="fas fa-map-marker-alt text-indigo-500 w-5"></i>
            <span class="text-gray-600 ml-3">${post.city}, ${post.district}</span>
        </div>
    `;
    $("#modalLocation").html(modalContent);

    const modalContact = `
        <div class="flex items-center">
            <i class="fas fa-phone text-green-500 w-5"></i>
            <span class="text-gray-600 ml-3">${post.contactNumber}</span>
        </div>
    `;
    $("#modalContact").html(modalContact);

    const modalLandmark = `
        <div class="flex items-center">
            <i class="fas fa-landmark text-purple-500 w-5"></i>
            <span class="text-gray-600 ml-3">${post.landmark}</span>
        </div>
    `;
    $("#modalLandmark").html(modalLandmark);

    const modalStatus = `
        <div class="flex items-center">
            <i class="fas fa-circle ${post.status === 'ACTIVE' ? 'text-green-400' : 'text-blue-400'} w-5"></i>
            <span class="text-gray-600 ml-3 font-semibold">${post.status}</span>
        </div>
    `;
    $("#modalStatus").html(modalStatus);

    const modalUser = `
        <div class="flex items-center">
            <i class="fas fa-user text-gray-400 w-5"></i>
            <span class="text-gray-500 ml-3">${post.finderName} • ${post.postDate}</span>
        </div>
    `;
    $("#modalUser").html(modalUser);

    // Add extra info
    const modalExtra = `
        <div class="flex items-center">
            <i class="fas fa-info-circle text-blue-500 w-5"></i>
            <span class="text-gray-600 ml-3">${post.color} • ${post.gender}</span>
        </div>
    `;
    $("#modalExtra").html(modalExtra);

    $("#postModal").removeClass("hidden");
    document.body.style.overflow = 'hidden';
});

// Close modal
$("#closeModal").on("click", function () {
    $("#postModal").addClass("hidden");
    document.body.style.overflow = 'auto';
});

$("#postModal").on("click", function (e) {
    if (e.target.id === "postModal") {
        $("#postModal").addClass("hidden");
        document.body.style.overflow = 'auto';
    }
});

