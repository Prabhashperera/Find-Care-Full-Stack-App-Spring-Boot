$(document).ready(function () {
    console.log("DOC READY")
    loadUserPosts();
});

function loadUserPosts() {
    let userName = localStorage.getItem("userName");
    console.log(userName);
    $.ajax({
        url: `http://localhost:8080/api/found/loaduserpost/${userName}`,
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken") // if using JWT
        },
        success: function (response) {
            const postsContainer = $("#postsContainer");
            postsContainer.empty();

            if (response.status === 200 && response.data && response.data.length > 0) {
                $("#emptyState").hide();

                response.data.forEach(post => {
                    const postCard = `
<div class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition-shadow mb-6 flex flex-col md:flex-row">
    
    <!-- Pet Image -->
    ${post.photoUrl ? `
    <div class="md:w-1/3 w-full h-48 md:h-auto">
        <img src="${post.photoUrl}" alt="Pet photo" class="w-full h-full object-cover">
    </div>` : ''}

    <!-- Details -->
    <div class="p-6 flex-1 flex flex-col justify-between">
        <div>
            <!-- Header -->
            <div class="flex items-center justify-between mb-3">
                <div class="flex items-center space-x-3">
                    <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center text-xl">
                        ${post.petType === "Cat" ? "🐱" : "🐶"}
                    </div>
                    <div>
                        <h3 class="font-semibold text-gray-900 text-lg">${post.petType} - ${post.breed}</h3>
                        <p class="text-sm text-gray-500">Posted on ${post.postDate}</p>
                    </div>
                </div>
                <span class="px-3 py-1 bg-green-100 text-green-700 text-sm rounded-full font-medium">${post.status}</span>
            </div>

            <!-- Info Grid -->
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-2 text-gray-600 text-sm mb-4">
                <p><strong>Color:</strong> ${post.color}</p>
                <p><strong>Gender:</strong> ${post.gender}</p>
                <p><strong>District:</strong> ${post.district}</p>
                <p><strong>City:</strong> ${post.city}</p>
                <p class="sm:col-span-2"><strong>LandMark:</strong> ${post.landmark}</p>
                <p><strong>Finder:</strong> ${post.finderName}</p>
                <p><strong>Contact:</strong> ${post.contactNumber}</p>
            </div>

            <!-- Description -->
            <p class="text-gray-700 mb-4"><strong>Description:</strong> ${post.postDescription}</p>
        </div>

        <!-- Actions -->
        <div class="flex space-x-3 mt-auto">
            <button class="editBtn px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition text-sm" data-postid="${post.postID}">Edit</button>
            <button class="deleteBtn px-4 py-2 bg-red-100 text-red-700 rounded-lg hover:bg-red-200 transition text-sm" data-postid="${post.postID}">Delete</button>
        </div>
    </div>
</div>
`;
                    postsContainer.append(postCard);
                });
            } else {
                $("#emptyState").show();
            }
        },
        error: function () {
            $("#emptyState").show();
        }
    });
}
