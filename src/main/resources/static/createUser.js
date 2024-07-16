
let newUserForm = document.forms["newUserForm"];

function addUser() {
    newUserForm.addEventListener('submit', event => {
        event.preventDefault()
        let newUserRoles = []
        for (let i = 0; i < newUserForm.roles.options.length; i++) {
            if (newUserForm.roles.options[i].selected) {
                newUserRoles.push({
                    id: newUserForm.roles.options[i].value,
                    authority: "ROLE_" + newUserForm.roles.options[i].text
                })
            }
        }
        console.log(newUserRoles);
        fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: newUserForm.id.value,
                roles: newUserRoles,
                firstName: newUserForm.firstName.value,
                lastName: newUserForm.lastName.value,
                age: newUserForm.age.value,
                email: newUserForm.email.value,
                password: newUserForm.password.value
            })
        }).then(() => {
            newUserForm.reset()
            getAllUsers()
            $('#usersTable').click()
        })
    })
}