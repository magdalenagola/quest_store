// export default class FormValidator {
//     constructor(form, btn) {
//         this.form = form;
//         this.btn = btn;
//     }
//
//     validate() {
//         const form = this.form;
//         const errorMessage = form.querySelector('.form__error-message');
//         const inputsList = form.getElementsByTagName('input');
//         const areValid = [];
//
//         init();
//
//         function init() {
//             for (let i = 0; i < inputsList.length; i++) {
//                 areValid[i] = true;
//             }
//         }
//
//         removeAllErrors();
//
//         function removeAllErrors() {
//             for (let i = 0; i < inputsList.length; i++) {
//                 errorMessage.innerText = '';
//                 inputsList[i].classList.remove('input--error');
//             }
//         }
//
//         this.btn.onclick = (e) => {
//             e.preventDefault();
//             for (let i = 0; i < inputsList.length; i++) {
//                 checkRequiredFields(i);
//                 checkEmail(i);
//                 checkName(i);
//                 checkSalary(i);
//             }
//
//             function removeInputError(i) {
//                 inputsList[i].classList.remove('input-error');
//                 areValid[i] = true;
//             }
//
//             function removeError() {
//                 for (let i = 0; i < areValid.length; i++) {
//                     console.log(areValid);
//                     if (areValid[i] !== false) {
//                         console.log('removing');
//                         inputsList[i].classList.remove('input--error');
//                     }
//                 }
//             }
//
//             function throwError(i, message) {
//                 inputsList[i].classList.add('input--error');
//                 errorMessage.innerText = message;
//                 areValid[i] = false;
//             }
//
//             function checkRequiredFields(i) {
//                 if (inputsList[i].hasAttribute('required')) {
//                     if (inputsList[i].value !== true) {
//                         throwError(i, 'Please, fill all the required fields');
//                     } else {
//                         removeError(i);
//                     }
//                 }
//             }
//
//             function checkEmail(i) {
//                 if (inputsList[i].name === 'email') {
//                     if ((/\S+@\S+\.\S+/.test(inputsList[i].value)) !== true) {
//                         throwError(i, 'Please, enter a valid email');
//                     } else {
//                         console.log('emailvslifdfs');
//                         removeInputError(i);
//                     }
//                 }
//             }
//
//             function checkName(i) {
//                 if (inputsList[i].name === 'name') {
//                     if ((/^[a-zA-Z -]*$/.test(inputsList[i].value)) !== true) {
//                         throwError(i, 'Please, enter a valid name');
//                     } else if ((/^[a-zA-Z -]*$/.test(inputsList[i].value)) !== true) {
//                         throwError(i, 'Please, enter a valid name');
//                     } else {
//                         console.log('namevslifdfs');
//                         removeInputError(i);
//                     }
//                 } else if (inputsList[i].name === 'surname') {
//                     if ((/^[a-zA-Z -]*$/.test(inputsList[i].value)) !== true) {
//                         throwError(i, 'Please, enter a valid last name');
//                     } else {
//                         console.log('surnamevslifdfs');
//                         removeInputError(i);
//                     }
//                 }
//             }
//
//             function checkSalary(i) {
//                 if (inputsList[i].name == 'salary') {
//                     if ((/^[1-9.]*$/.test(inputsList[i].value)) !== true) {
//                         throwError(i, 'Please, enter a valid salary number');
//                         inputsList[i].value = '';
//                     } else {
//                         console.log('salaryvslifdfs');
//                         removeInputError(i);
//                     }
//                 }
//
//                 removeError();
//
//                 function checkIsTrue(input) {
//                     if (input) {
//                         return input;
//                     }
//                 }
//
//                 if (areValid.every(checkIsTrue)) {
//                     return true;
//                 } else {
//                     return false;
//                 }
//             }
//         }
//     }
// }

export default class FormValidator {
    constructor(form, btn) {
        this.form = form;
        this.btn = btn;
    }

     validate() {
        const errorMessage = this.form.querySelectorAll('.form__error-message');
        const inputsList = this.form.getElementsByTagName('input');
        console.log(inputsList);
        if (errorMessage) {
            for (let i = 0; i < inputsList.length; i++) {
                inputsList[i].classList.remove('input--error');
            }
            for (let i = 0; i < errorMessage.length; i++) {
                errorMessage[i].textContent = '';
            }
        }
        console.log(this.btn);
        this.btn.onclick = (e) => {
            alert(this.btn);
            e.preventDefault();
            let result = getIsValid();
            console.log("result " + result);

            this.form.setAttribute('isValid', result);
            return result;
        };

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

            if (areValid.every(checkIsTrue)) {
                removeErrorMessage();
                return true;
            }
            return false;

            console.log(areValid);
        }

    }
}