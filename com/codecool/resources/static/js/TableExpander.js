export default function expandTableContent() {
    const transactionItems = document.querySelectorAll('.transactions__item');
    const transtactionItemsContent = document.querySelectorAll('.transactions__content-wrapper');
    const userItems = document.querySelectorAll('.user__item');
    const userItemsContent = document.querySelectorAll('.user__content-wrapper');
    let userSalary = document.querySelectorAll('p.user__salary');

    if (userItems.length === 1 || transactionItems.length === 1) {
        setTimeout(expandTableContent, 2000)
    }

    for (let i = 0; i < transactionItems.length; i++) {
        transactionItems[i].addEventListener('click', () => {
            transtactionItemsContent[i].classList.toggle('transactions__content-wrapper--opened');
        })
    }

    for (let i = 1; i < userItems.length; i++) {
        if (userSalary[i] !== undefined) {
            userSalary[i].textContent += ' $';
        }

        if (window.outerWidth <= 768) {
            userItems[i].addEventListener('click', (e) => {
                if (e.toElement.tagName == 'H3') {
                    userItemsContent[i - 1].classList.toggle('user__content-wrapper--opened');
                }
            })
        }
    }
}