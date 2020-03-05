export default class DeleteStudent {

    deleteStudent(studentId) {
        const xmlHttpRequest =  new XMLHttpRequest();
        deleteStudent(studentId);
        xmlHttpRequest.onreadystatechange = function () {
            console.log(xmlHttpRequest.responseText)
            if (xmlHttpRequest.readyState == xmlHttpRequest.DONE) {
                if (xmlHttpRequest.status === 200) {
                    if(xmlHttpRequest.responseText == "deleted"){
                        alert("SUCCESSFULLY DELETED")
                        location.reload();
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