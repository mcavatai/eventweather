$ ->
  $.get "/weather", (items) ->
    $.each items, (index, item) ->
      $("#weather").append $("<li>").text item