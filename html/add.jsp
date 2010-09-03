<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Legg til quote</title>
<style type="text/css">
body {font-family: monospace;}
hr {
	border-style: solid;
	border-color: black;
	border-width: 1px; 
}
</style>
</head>
<body>
<form action="/post/add" method="post">
ditt nick:<br>
<input type=text name="nick" size=20><br>
dato, YYYY-MM-DD (blank for dagens dato):<br>
<input type=text name="date" size=20><br>
quote:<br>
<textarea name="quote" rows="20" cols="80"></textarea><br>
Prøv å bruke sånn ca. samme timestamp-format (HH:MM) o.l. som eksisterende quotes :-)<br><br>
<input type="submit" value="Legg til">
</form>
<a href="quotes.jsp">&lt;- Tilbake til quotes</a>
</body>
</html>

