<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body>
        <h1 align="center">Rock Paper Scissor Game Dashboard</h1>
        <h2> Welcome ${pageContext.session.getAttribute("username")} </h2>
        <fieldset>
            <legend>Select your Choice</legend>
            <form action = player method = post>
                <input type="radio" name="pick" value="rock" checked>Rock<br>
                <input type="radio" name="pick" value="paper">Paper<br>
                <input type="radio" name="pick" value="scissors">Scissors<br>
                <input type="submit" name="submit">
            </form>
        </fieldset>    
        <br>
        <br>
        <!-- <h3>Save History | Show History | Clear History</h3> -->
        <a href="${pageContext.request.contextPath}/player?history=save"><h4 align="inline">Save History</h4></a> 
        <a href="${pageContext.request.contextPath}/showhistory.jsp"><h4 align="inline">Show History</h4></a>
        <a href="${pageContext.request.contextPath}/player?history=clear"><h4 align="inline">Start Fresh</h4></a>
        <a href="${pageContext.request.contextPath}/player?history=saveclear"><h4 align="inline">Clear Saved History</h4></a>
            
    </body>
</html>
