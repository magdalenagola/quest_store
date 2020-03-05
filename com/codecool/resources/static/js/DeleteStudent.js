import InteractiveStyles from "./InteractiveStyles.js";

export default class DeleteStudent {

    deleteStudent(studentId) {
        const xmlHttpRequest =  new XMLHttpRequest();
        const interactiveStyles = new InteractiveStyles();
        const popup = document.querySelector('.popup');
        deleteStudent(studentId);
        xmlHttpRequest.onreadystatechange = function () {
            console.log(xmlHttpRequest.responseText)
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if(xmlHttpRequest.responseText == "deleted"){
                        const message = 'Successfully deleted!';
                        interactiveStyles.showPopup(popup, message);
                    }
                }
            }
        };


        function deleteStudent(studentId) {
            xmlHttpRequest.open('POST', `/mentor/students/delete/${studentId}`);
            xmlHttpRequest.send(JSON.stringify(studentId));
        }
    }
}