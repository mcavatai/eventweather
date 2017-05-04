<!doctype html>
<html>
    <head>
        <title><g:if test="${params.a == 'okay'}">OKAY!</g:if><g:else>Not Okay.</g:else></title>
        <meta charset="UTF-8">
    </head>
    <body>
        <g:if test="${params.a == 'okay'}">
        <h1>You done been bamboozled!</h1>
        <p>(Heck.)</p>
        <img src="http://ci.memecdn.com/9445409.gif"/>
        </g:if>
        <g:else>
        <h1>This is totally just a normal page!</h1>
        <p>Nothing to see here</p>
        </g:else>
    </body>
</html>
