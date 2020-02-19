export default class FormValidator {
    constructor(form, btn) {
        this.form = form;
        this.btn = btn;
    }

    validate() {
        const errorMessage = document.querySelectorAll('.form__error-message');
        this.btn.onclick = (e) => {
            e.preventDefault();
            if (getIsValid()) {
                this.form.setAttribute('isValid', 'true');
            } else {
                this.form.setAttribute('isValid', 'false');
            }
        }

        function getIsValid() {
            const inputsList = document.getElementsByTagName('input');
            const areValid = [];
            for (let i = 0; i < inputsList.length; i++) {
                areValid.push(true);
                checkRequiredFields(i);
                checkEmail(i);
                checkName(i);
                checkSalary(i);
            }

            function throwError(i, message) {
                inputsList[i].classList.add('input--error');
                for(let i = 0; i < errorMessage.length; i++) {
                    errorMessage[i].textContent = message;
                }
                areValid[i] = false;
            }

            function removeErrorBorder() {
                for (let i = 0; i < areValid.length; i++) {
                    if (areValid[i] === true) {
                        inputsList[i].classList.remove('input--error');
                    }
                }
            }

            function removeErrorMessage() {
                for (let i = 0; i < inputsList.length; i++) {
                    for(let i = 0; i < errorMessage.length; i++) {
                        errorMessage[i].textContent = '';
                    }
                }
            }

            function checkRequiredFields(i) {
                if (inputsList[i].hasAttribute('required') && !inputsList[i].value) {
                    throwError(i, 'Please, fill all the required fields');
                }
            }

            function checkEmail(i) {
                if (inputsList[i].name === 'email' && !(/\S+@\S+\.\S+/.test(inputsList[i].value))) {
                    throwError(i, 'Please, enter a valid email');
                }
            }

            function checkName(i) {
                if (inputsList[i].name === 'name' && !(/^[a-zA-Z -]*$/.test(inputsList[i].value))) {
                    throwError(i, 'Please, enter a valid name');
                } else if (inputsList[i].name === 'surname' && !(/^[a-zA-Z -]*$/.test(inputsList[i].value))) {
                    throwError(i, 'Please, enter a valid last name');
                }
            }

            function checkSalary(i) {
                if (inputsList[i].name == 'salary' && !(/^[1-9.]*$/.test(inputsList[i].value))) {
                    throwError(i, 'Please, enter a valid salary number');
                    inputsList[i].value = '';
                }
            }

            function checkIsTrue(input) {
                if (input) {
                    return input;
                }
            }

            removeErrorBorder();

            if (areValid.every(checkIsTrue)) {
                removeErrorMessage();
                return true;
            }
        }
    }
}