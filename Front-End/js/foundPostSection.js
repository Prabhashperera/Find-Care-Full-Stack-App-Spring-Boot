$(document).ready(function() {
    const accessToken = localStorage.getItem("accessToken"); // if you want auth

    $.ajax({
        url: "http://localhost:8080/api/found/getall",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + accessToken
        },
        success: function(response) {
            if (response.status === 200 && response.data) {
                const postsContainer = $("#postsContainer");
                postsContainer.empty(); // clear container first

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
        error: function(err) {
            console.error("Error fetching posts:", err);
            $("#postsContainer").html("<p class='text-center text-red-500'>Failed to load posts.</p>");
            apiRequest("http://localhost:8080/api/found/getall" , "GET", null);
        }
    });
});


function apiRequest(url, method, data) {
    return $.ajax({
        url: url,
        method: method,
        data: data,
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        }
    }).fail(function(xhr) {
        if (xhr.status === 401) {
            // Access token expired → try refresh
            return $.ajax({
                url: "/refresh-token",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ refreshToken: localStorage.getItem("refreshToken") })
            }).then(function(response) {
                // Save new access token
                localStorage.setItem("accessToken", response.accessToken);
                // Retry original request
                return apiRequest(url, method, data);
            }).fail(function() {
                // Refresh token expired → logout
                alert("Session expired! Please log in again.");
                localStorage.removeItem("accessToken");
                localStorage.removeItem("refreshToken");
                window.location.href = "/login.html";
            });
        }
    });
}