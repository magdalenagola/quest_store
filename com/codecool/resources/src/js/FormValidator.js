export default class FormValidator {
    constructor(form, btn) {
        this.form = form;
        this.btn = btn;
    }

    validate() {
        let isFormValid;
        const errorMessage = document.querySelector('.form__error-message');
        this.btn.onclick = (e) => {
            e.preventDefault();
            if (checkRequiredFields()) {
                this.form.setAttribute('isValid', 'true');
            } else {
                this.form.setAttribute('isValid', 'false');
            }
        }

        function checkRequiredFields() {
            const inputsList = document.getElementsByTagName('input');
            const areValid = [];
            for (let i = 0; i < inputsList.length; i++) {
                areValid.push(false);
                if (inputsList[i].hasAttribute('required') && !inputsList[i].value) {
                    inputsList[i].classList.add('input--error');
                    errorMessage.textContent = 'Please, fill all the required fields';
                } else if (inputsList[i].name == 'email' && !(/\S+@\S+\.\S+/.test(inputsList[i].value))) {
                    inputsList[i].classList.add('input--error');
                    errorMessage.textContent = 'Please, enter a valid email';
                } else if(inputsList[i].name == 'name' && !(/^[a-zA-Z ]*$/.test(inputsList[i].value))) {
                    inputsList[i].classList.add('input--error');
                    errorMessage.textContent = 'Please, enter a valid name';
                } else if (inputsList[i].name == 'surname' && !(/^[a-zA-Z ]*$/.test(inputsList[i].value))) {
                    inputsList[i].classList.add('input--error');
                    errorMessage.textContent = 'Please, enter a valid last name';
                } else {
                    inputsList[i].classList.remove('input--error');
                    areValid[i] = true;
                }
            }
            for (let isValid of areValid) {
                if (isValid === true) {
                    isFormValid = true;
                } else {
                    isFormValid = false;
                    break;
                }
            }

            if (isFormValid) {
                errorMessage.textContent = '';
                return true;
            }
        }
    }
}