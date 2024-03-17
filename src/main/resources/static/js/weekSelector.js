function getCurrentWeekNumber() {
    var today = new Date();
    var firstDayOfYear = new Date(today.getFullYear(), 0, 1);
    var diff = today - firstDayOfYear;
    var oneWeek = 1000 * 60 * 60 * 24 * 7;
    var weekNumber = Math.ceil((diff + 1) / oneWeek);
    return weekNumber;
}
var currWeek = getCurrentWeekNumber();
var selectWeek = document.getElementById("week_number");

// Populate the select element with week options
for (var i = currWeek; i <= 52; i++) {
    var option = document.createElement("option");
    option.value = i;
    option.text = "Week " + i;
    selectWeek.appendChild(option);
}