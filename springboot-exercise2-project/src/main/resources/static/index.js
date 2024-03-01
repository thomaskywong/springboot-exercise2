document.addEventListener('DOMContentLoaded', function () {
  var coinFilter = document.getElementById('coinFilter');
  var coinRows = document.querySelectorAll('.coinRow');

  coinFilter.addEventListener('input', function () {
    var filter = coinFilter.value.toUpperCase();

    coinRows.forEach(function (row) {
      var coinId = row.querySelector('td:nth-child(2)').textContent.toUpperCase();

      if (coinId.indexOf(filter) > -1) {
        row.style.display = ''; // Show the row
      } else {
        row.style.display = 'none'; // Hide the row
      }
    });
  });
});