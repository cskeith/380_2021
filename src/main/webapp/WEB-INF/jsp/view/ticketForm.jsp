<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Create a Ticket</h2>
        <form method="POST" action="tickets">
            <input type="hidden" name="action" value="create"/>
            Customer Name<br/>
            <input type="text" name="customerName"><br/><br/>
            Subject<br/>
            <input type="text" name="subject"><br/><br/>
            Body<br/>
            <textarea name="body" rows="5" cols="30"></textarea><br/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
