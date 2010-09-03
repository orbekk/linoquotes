<%@ page contentType="text/html; charset=UTF-8"
         import="java.util.List"
%>
<html>
<head>
<meta name="robots" content="noindex, nofollow" />
<title>Quote approval</title>
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

<%
if (lq.UserUtil.isAuthenticated()) {
    out.println("Logget inn som: " + lq.UserUtil.getAuthenticatedEmail() + "<br>");

    if (!lq.Strings.nullOrEmpty(request.getParameter("id"))) {
        String action = request.getParameter("action");
        if (action != null && (action.equals("approve") || action.equals("reject"))) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                if (action.equals("approve")) {
                  lq.QuoteUtils.approveQuote(id);
                  out.println("Godkjente quote #" + id);
                }
                else {
                  lq.QuoteUtils.rejectQuote(id);
                  out.println("Avviste quote #" + id);
                }
            }
            catch (NumberFormatException e) {
                out.println("lmao for en gimp hax<br>");
            }
        }
        else {
            out.println("Feilkode π/3");
        }
        out.println("<p><p>");
    }

    List<lq.Quote> pendingQuotes = lq.QuoteUtils.getQuotesPendingApproval();

    for (lq.Quote quote : pendingQuotes) {
        String nick = lq.Strings.escape(quote.getAuthor());
        String timestamp = lq.DateUtil.timestampFormat.format(quote.getTimestamp());
        String date = lq.DateUtil.dateFormat.format(quote.getQuoteDate());
        String content = lq.Strings.escape(quote.getContent());

        out.println("<br><pre>");
        out.println("Fra " + quote.getIp() + ", " + timestamp);
        out.println("Nick: " + nick);
        out.println("Date: " + date);
        out.println();
        out.println(content);
        out.println("</pre>");
        out.println("<a href=\"approve.jsp?"
            + "id=" + quote.getId() + "&"
            + "action=reject\">avvis</a>");
        out.println(", ");
        out.println("<a href=\"approve.jsp?"
            + "id=" + quote.getId() + "&"
            + "action=approve\">godkjenn</a>");
        out.println("<hr>");
    }

  String logoutUrl = lq.UserUtil.getLogoutUrl(request.getRequestURI());
  out.println("<a href=\"" + logoutUrl + "\">Logg ut</a>");
}
else {
  String loginUrl = lq.UserUtil.getLoginUrl(request.getRequestURI());
  out.println("<a href=\"" + loginUrl + "\">Logg inn</a>");
}
%>
</body>
</html>
