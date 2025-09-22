// Loading FOUND POSTS
$(document).ready(function () {
    console.log("DOC READY")
    $(".user_Name_Profile").text(localStorage.getItem("userName"));
    loadUserFoundPosts();
});
function loadUserFoundPosts() {
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
            <select class="foundStatusDropdown px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition text-sm cursor-pointer" 
            data-postid="${post.postID}">
                <option value="Active" ${post.status === 'Active' ? 'selected' : ''}>Active</option>
                <option value="Success" ${post.status === 'Success' ? 'selected' : ''}>Success</option>
            </select>
            <button class="foundDeleteBtn px-4 py-2 bg-red-100 text-red-700 rounded-lg hover:bg-red-200 transition text-sm" data-postid="${post.postID}">Delete</button>
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

// Loading LOST POSTS
$("#lostPostsTab").on("click", function () {
    loadUserLostPosts();
    console.log("LOST PostsTab clicked");
});
function loadUserLostPosts() {
    let userName = localStorage.getItem("userName");
    console.log(userName);
    $.ajax({
        url: `http://localhost:8080/api/lost/loaduserpost/${userName}`,
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken") // if using JWT
        },
        success: function (response) {
            const postsContainer = $("#lostPostsContainer");
            postsContainer.empty();

            if (response.status === 200 && response.data && response.data.length > 0) {
                $("#emptyState").hide();
                console.log(response.data);
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
                <p class="sm:col-span-2"><strong>Address:</strong> ${post.address}</p>
                <p><strong>Finder:</strong> ${post.finderName}</p>
                <p><strong>Contact:</strong> ${post.contactNumber}</p>
            </div>

            <!-- Description -->
            <p class="text-gray-700 mb-4"><strong>Description:</strong> ${post.postDescription}</p>
        </div>

        <!-- Actions -->
        <div class="flex space-x-3 mt-auto">
            <select class="lostStatusDropdown px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition text-sm cursor-pointer" 
            data-postid="${post.postID}">
                <option value="Active" ${post.status === 'Active' ? 'selected' : ''}>Active</option>
                <option value="Success" ${post.status === 'Success' ? 'selected' : ''}>Success</option>
            </select>
            <button class="lostDeleteBtn px-4 py-2 bg-red-100 text-red-700 rounded-lg hover:bg-red-200 transition text-sm" data-postid="${post.postID}">Delete</button>
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


// DELETE FOUND POSTS
$(document).on('click', '.foundDeleteBtn', function() {
    const postId = $(this).data('postid');
    const button = $(this);

    // Show modern confirmation popup
    Swal.fire({
        title: 'Are you sure?',
        text: "This post will be permanently deleted!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'Cancel',
        didOpen: () => {
            // Optional: you can focus confirm button automatically
            Swal.getConfirmButton().focus();
        }
    }).then((result) => {
        if (result.isConfirmed) {
            // Show loading popup while AJAX runs
            Swal.fire({
                title: 'Deleting...',
                allowOutsideClick: false,
                didOpen: () => Swal.showLoading()
            });

            // jQuery AJAX request
            $.ajax({
                url: `http://localhost:8080/api/found/delete/${postId}`,
                method: 'DELETE',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("accessToken")
                },
                success: function(response) {
                    Swal.fire(
                        'Deleted!',
                        'Your post has been deleted.',
                        'success'
                    );
                    button.closest('.postCard').remove();
                    loadUserFoundPosts(); // refresh list if needed
                },
                error: function(xhr, status, error) {
                    Swal.fire(
                        'Error!',
                        `Failed to delete post: ${xhr.responseText || error}`,
                        'error'
                    );
                    console.error("Error deleting post:", xhr.responseText || error);
                }
            });
        }
    });
});

// DELETE LOST POSTS
$(document).on('click', '.lostDeleteBtn', function() {
    const postId = $(this).data('postid');
    const button = $(this);

    // Show modern confirmation popup
    Swal.fire({
        title: 'Are you sure?',
        text: "This post will be permanently deleted!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'Cancel',
        didOpen: () => {
            // Optional: you can focus confirm button automatically
            Swal.getConfirmButton().focus();
        }
    }).then((result) => {
        if (result.isConfirmed) {
            // Show loading popup while AJAX runs
            Swal.fire({
                title: 'Deleting...',
                allowOutsideClick: false,
                didOpen: () => Swal.showLoading()
            });

            // jQuery AJAX request
            $.ajax({
                url: `http://localhost:8080/api/lost/delete/${postId}`,
                method: 'DELETE',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("accessToken")
                },
                success: function(response) {
                    Swal.fire(
                        'Deleted!',
                        'Your post has been deleted.',
                        'success'
                    );
                    button.closest('.postCard').remove();
                    loadUserLostPosts(); // refresh list if needed
                },
                error: function(xhr, status, error) {
                    Swal.fire(
                        'Error!',
                        `Failed to delete post: ${xhr.responseText || error}`,
                        'error'
                    );
                    console.error("Error deleting post:", xhr.responseText || error);
                }
            });
        }
    });
});



// Listen for change on any status dropdown for FoundPosts
$(document).on('change', '.foundStatusDropdown', function() {
    const postID = $(this).data('postid');
    const status = $(this).val(); // get selected value

    $.ajax({
        url: "http://localhost:8080/api/found/changestatus",
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken") // if using JWT
        },
        data: { postID, status },
        success: function(response) {
            console.log("Status changed ", response);
            location.reload();
        },
        error: function(err) {
            console.error("Failed to change status", err);
        }
    });
});


// Listen for change on any status dropdown for LostPosts
$(document).on('change', '.lostStatusDropdown', function() {
    const postID = $(this).data('postid');
    const status = $(this).val(); // get selected value

    $.ajax({
        url: "http://localhost:8080/api/lost/changestatus",
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("accessToken") // if using JWT
        },
        data: { postID, status },
        success: function(response) {
            console.log("Status changed ", response);
            location.reload();
        },
        error: function(err) {
            console.error("Failed to change status", err);
        }
    });
});



