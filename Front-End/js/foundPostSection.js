$(document).ready(function() {
    const token = localStorage.getItem("token"); // if you want auth

    $.ajax({
        url: "http://localhost:8080/api/found/getall",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token
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
        }
    });
});
