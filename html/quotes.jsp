<%@ page contentType="text/html; charset=UTF-8" 
         import="java.util.List"
%>
<html>
<head>
<meta name="robots" content="noindex, nofollow" />
<title>Quotes fra #linux.no på freenode</title>
<style type="text/css">
body {font-family: monospace;}
hr {
	border-style: solid;
	border-color: black;
	border-width: 1px; 
}
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" link="#000000" vlink="#000000">

<pre>
                     _ _  
                    | (_)_ __  _   ___  __  _ __   ___  
              _|_|_ | | | '_ \| | | \ \/ / | '_ \ / _ \ 
              _|_|_ | | | | | | |_| |)  ( _| | | | (_) | 
               | |  |_|_|_| |_|\__,_/_/\_(_)_| |_|\___/ 
             -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                   Quotes fra #linux.no på freenode
                  Klikk <a href="add.jsp">her</a> for å legge til en quote

                    Sortér etter <a href="quotes.jsp?order=date">dato</a> | <a href="quotes.jsp?order=score">score</a> | <a href="quotes.jsp?order=number">id</a>
</pre>
<hr>

<script>

function ajaxvote(id, value) { 
    var http = new XMLHttpRequest();
    http.open("GET","ajaxvote.jsp?id="+id+"&vote="+value);
    http.onreadystatechange=function() {
        if(http.readyState==4) {
            document.getElementById("v"+id).innerHTML = http.responseText;
        }
    }
    http.send(null);
}
</script>


<%

String order = request.getParameter("order");
 
List<lq.Quote> quotes;

if (order == null) {
    quotes = lq.QuoteUtil.getQuotesOrderedByIdDesc();
} else if(order.equals("id")) {
    quotes = lq.QuoteUtil.getQuotesOrderedByIdDesc();
} else if(order.equals("score")) {
    quotes = lq.QuoteUtil.getQuotesOrderedByScoreDesc();
} else if(order.equals("date")) {
    quotes = lq.QuoteUtil.getQuotesOrderedByDateDesc();
} else {
    quotes = lq.QuoteUtil.getQuotesOrderedByIdDesc();
}

lq.Printer printer = new lq.Printer(out); 
for (lq.Quote quote : quotes) {
    printer.printQuote(quote);
}

%>
<center>
<br>
<p>linoquotes v.2 © 2004-2010 Erlend Hamberg, Vidar Holen, Kjetil Ørbekk, John H. Anthony.
<br>See <a href="http://github.com/orbekk/linoquotes">http://github.com/orbekk/linoquotes</a>
  for details.</p>
<p>The quotes on this page are copyright their respective owners and submitters.</p>
<p>Powered by Google AppEngine.</p>
</center>
</body>
</html>
