# PersistentRPS

## A Multiuser Rock-Paper-Scissor Game

### Following Functionalities Added:

1. Multiuser login
2. Dashboard view with Game Submission (Result thereafter), 'Show History', 'Clear History', 'Start Fresh' (Clear In-memory History)

### Missing Features

1. Control check : An exception occurs when user tries to save a 'null' in memory history to database.
2. Control Flow : Controller server doesn't throw errors/forwards when tainted arguments are passed to "Get" methods.

### Dependecies 

1. A in-line database entry is added in all view-jsps like "showhistory.jsp"

### Technologies in Play
1.JSP {JSTL} (View)
2.Servlet (Controller)
3.Sqlite3 (Model)
4. Tomcat 8.0.10 (Webserver)

