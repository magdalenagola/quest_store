export default function expandTableContent() {
    const transactionItems = document.querySelectorAll('li.transactions__item');
    const transtactionItemsContent = document.querySelectorAll('.transactions__content-wrapper');
    const userItems = document.querySelectorAll('li.user__item');
    const userItemsContent = document.querySelectorAll('.user__content-wrapper');
    let userSalary = document.querySelectorAll('p.user__salary');
    for (let i = 0; i < transactionItems.length; i++) {
        transactionItems[i].addEventListener('click', () => {
            transtactionItemsContent[i].classList.toggle('transactions__content-wrapper--opened');
        })
    }
    for (let i = 1; i < userItems.length; i++) {
        userSalary[i-1].textContent += ' $';
        userItems[i].addEventListener('click', (e) => {
            if(e.toElement.tagName == 'H3') {
                userItemsContent[i].classList.toggle('user__content-wrapper--opened');
            }
        })
    }

    
}