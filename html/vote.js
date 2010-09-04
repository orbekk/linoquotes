function vote(id, value) {
    var http = new XMLHttpRequest();
    http.open("GET","/vote?id="+id+"&vote="+value);
    http.onreadystatechange=function() {
        if(http.readyState==4) {
            document.getElementById("v"+id).innerHTML = http.responseText;
        }
    }
    http.send(null);
}
