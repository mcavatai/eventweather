<!doctype html>
<html>
    <head>
        <title>Weather Page!</title>
        <meta charset="UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <%@ page import = "helloworld.OpenWeatherModel" %>
    </head>
    <body>
        <form>
            <table width="75%">
                <tr>
                    <td width="48%">What is your zipcode?</td>
                    <td width="52%">
                        <input type="text" id="zipcode" name="zip"/>
                    </td>
                </tr>
            </table>
            <p>
                <input type="button" id="submitBtn" value="Submit" />
                <input type="reset" name="Reset" value="Reset form" />
            </p>
            <p>
                <input type="button" id="killBtn" value="Clear all history" />
            </p>
            <p>${params.zip}</p>
            <g:if test="${params.zip != null}">
                <% String result;
                    OpenWeatherModel model = new OpenWeatherModel(params.zip);
                    if (model.getValid()) {
                        result = ("Location: " + model.getName());
                        result += ("\nCurrent Temp: " + model.getTemp() + "K");
                        result += ("\nCurrent Humidity: " + model.getHumidity() + "%");
                        result += ("\nCurrent Wind Speed: " + model.getWindSpeed() + " m/s");
                        result += ("\nCurrent Overcast: " + model.getOvercast() + "%");
                        result += ("\nSearch count = " + "${session.tobedisplayed}");
                    }
                %>
                <p>${result.encodeAsHTML().replace('\n', '<br/>').encodeAsRaw()}</p>
                <img style="width:90px;height:153px;" src="http://2.bp.blogspot.com/-DUKvidboBJc/Uht0S9ZuywI/AAAAAAAACB4/gdNcws-0KBc/s1600/jesus-christ-dancing-disco-dance-animated.gif"/>
            </g:if>
        </form>
        <script type="text/javascript">
        $(document).ready(function() {
            $('#submitBtn').on('click', function(event){
                var name = document.getElementById("zipcode").value;
                if (name != undefined && name != null && name.length != 0) {
                    window.location = '/hello/weather?zip=' + name;
                } else {
                    window.location = '/hello/weather';
                }
            });
            $('#killBtn').on('click', function(event) {
                window.location = '/hello/clrData';
            });
        });
        </script>
    </body>
</html>
