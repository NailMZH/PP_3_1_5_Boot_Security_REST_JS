let formAddNewUser = document.forms["formAddNewUser"];
formAddNewUser.roles.options[1].selected=true;
console.log(formAddNewUser);

createNewUser()
function createNewUser() {
    formAddNewUser.addEventListener("submit", async (ev) => {
        ev.preventDefault();
        let rolesId = [];
        for (let i =  0; i < formAddNewUser.roles.options.length; i++) {
            if (formAddNewUser.roles.options[i].selected) rolesId.push("ROLE_" + formAddNewUser.roles.options[i].text);
        }
        try {
            const response = await fetch("/api/users", {
                method: 'POST', //Записываем данные
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    firstName: formAddNewUser.firstName.value,
                    lastName: formAddNewUser.lastName.value,
                    age: formAddNewUser.age.value,
                    email: formAddNewUser.email.value,
                    password: formAddNewUser.password.value,
                    roles: rolesId
                })
            });

            if (!response.ok) {
                const errorText = await response.text();// Обработка ошибки
                alert(`Ошибка при создании нового пользователя: ${errorText}`);
                return;
            }

            formAddNewUser.reset();// Сброс формы и обновление таблицы
            $('#usersTable').click();
          getAllUsers();
        } catch (error) {
            console.error('Ошибка:', error);// Ловим и обробатываем ошибки
            alert('Произошла ошибка при создании пользователя. Пожалуйста, попробуйте еще раз.');
        }
    });
}