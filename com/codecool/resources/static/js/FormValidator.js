export default class FormValidator {
    constructor(form, btn) {
        this.form = form;
        this.btn = btn;
    }

     validate() {
        let result;
        const errorMessage = this.form.querySelectorAll('.form__error-message');
        const inputsList = this.form.getElementsByTagName('input');

        if (errorMessage) {
            for (let i = 0; i < inputsList.length; i++) {
                inputsList[i].classList.remove('input--error');
            }
            for (let i = 0; i < errorMessage.length; i++) {
                errorMessage[i].textContent = '';
            }
        }
        result = getIsValid();

        if (result) {
            this.form.setAttribute('isValid', 'true');
        }

        function getIsValid() {
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
                for (let i = 0; i < errorMessage.length; i++) {
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
                    for (let i = 0; i < errorMessage.length; i++) {
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
                return input;
            }

            removeErrorBorder();
            console.log(areValid);
            if (areValid.every(checkIsTrue)) {
                removeErrorMessage();
                return true;
            }
            return false;

        }
        return result;

    }
}