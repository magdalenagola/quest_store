export default function expandTableContent() {
    const transactionItems = document.querySelectorAll('li.transactions__item');
    const transtactionItemsContent = document.querySelectorAll('.transactions__content-wrapper');
    const mentorsItems = document.querySelectorAll('li.mentors__item');
    const mentorsItemsContent = document.querySelectorAll('.mentors__content-wrapper');
    let mentorsSalary = document.querySelectorAll('p.mentors__salary');
    for (let i = 0; i < transactionItems.length; i++) {
        transactionItems[i].addEventListener('click', () => {
            transtactionItemsContent[i].classList.toggle('transactions__content-wrapper--opened');
        })
    }
    for (let i = 1; i < mentorsItems.length; i++) {
        mentorsSalary[i-1].textContent += ' $';
        mentorsItems[i].addEventListener('click', (e) => {
            if(e.toElement.tagName == 'H3') {
                mentorsItemsContent[i].classList.toggle('mentors__content-wrapper--opened');
            }
        })
    }

    
}