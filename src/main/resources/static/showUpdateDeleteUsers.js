const userTable = $('#tbodyAllUserTable');
let editModalForm = document.forms['editModalForm']
let deleteModalForm = document.forms['deleteModalForm']


$(async function () {
    await getAuthUser();
    await getAllUsers();
    editUser();
    deleteUser()
})

async function getUser(id) {
    let response = await fetch('/api/users/' + id)
    return await response.json()
}

async function fillModalForm(form, modal, id) {
    modal.show()
    let user = await getUser(id)
    form.id.value = user.id
    form.roles.value = user.roles[0].authority
    form.firstName.value = user.firstName
    form.lastName.value = user.lastName
    form.age.value = user.age
    form.email.value = user.email
}


// Get Authenticated User
async function getAuthUser() {
    fetch("/api/user")
        .then(response => response.json())
        .then(data => {
            $('#authenticatedUserEmail').append(data.email);
            let roles = data.roles.map(role => ' ' + role.authority.substring(5))
            $('#authenticatedUserRoles').append(roles)
            let user = `$(
                    <tr>
                    <td>${data.id}</td>
                    <td>${data.firstName}</td>
                    <td>${data.lastName}</td>
                    <td>${data.age}</td>
                    <td>${data.email}</td>
                    <td>${data.roles.map(role => ' ' + role.authority.substring(5))}</td>
                    </tr>`;
            $('#userInfo').append(user);
        })
        .catch(error => console.log(error))
}

//Read
async function getAllUsers() {
    // const userTable = $('#tbodyAllUserTable');
    userTable.empty();
    fetch('/api/users')
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableWithUsers = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => ' ' + role.authority.substring(5))}</td>
                            
                            <td>
                                <button type="button" class="btn btn-info"
                                data-bs-toogle="modal"
                                data-bs-target="#editModal"
                                onclick="openEditModal(${user.id})">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" 
                                data-toggle="modal"
                                data-bs-target="#deleteModal"
                                onclick="openDeleteModal(${user.id})">Delete</button>
                            </td>
                            
                        </tr>)`
                userTable.append(tableWithUsers);
            })
        }).catch(error => console.log(error))
}

// User Editor
async function openEditModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#modalEdit'))
    await fillModalForm(editModalForm, modal, id)
}

function editUser() {
    editModalForm.addEventListener('submit', event => {
        event.preventDefault()
        let editedUserRoles = []
        for (let i = 0; i < editModalForm.roles.options.length; i++) {
            if (editModalForm.roles.options[i].selected) {
                editedUserRoles.push({
                    id: editModalForm.roles.options[i].value,
                    authority: 'ROLE_' + editModalForm.roles.options[i].text
                })
            }
        }

        fetch('/api/users', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editModalForm.id.value,
                roles: editedUserRoles,
                firstName: editModalForm.firstName.value,
                lastName: editModalForm.lastName.value,
                age: editModalForm.age.value,
                email: editModalForm.email.value,
                password: editModalForm.password.value
            })
        }).then(() => {
            $('#editModalCloseButton').click()
            getAllUsers()
        })
    })
}

// Delete User
async function openDeleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'))
    await fillModalForm(deleteModalForm, modal, id)
    switch (deleteModalForm.roles.value) {
        case '1':
            deleteModalForm.roles.value = 'USER'
            break
        case '2':
            deleteModalForm.roles.value = 'ADMIN'
            break
    }
}

function deleteUser() {
    deleteModalForm.addEventListener("submit", event => {
        event.preventDefault()
        fetch("/api/users/" + deleteModalForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#deleteModalCloseButton').click()
            getAllUsers()
        })
    })
}



