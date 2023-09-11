let allStocks = [];

// Sayfa yüklendiğinde hisseleri al
window.onload = function() {
    fetch('http://localhost:8080/api/get-bist-data/all')
    .then(response => response.json())
    .then(data => {
        allStocks = data;
    })
    .catch((error) => console.error('Error:', error));
};


function filterStocks(query) {
  const stockDropdown = document.getElementById('stockDropdown');
  stockDropdown.innerHTML = '';
  stockDropdown.style.display = query ? 'block' : 'none';

  allStocks.forEach((stock) => {
    if (stock.code.toLowerCase().includes(query.toLowerCase())) {
      const option = document.createElement('a');
      option.className = 'list-group-item list-group-item-action';
      option.innerHTML = stock.code;
      option.addEventListener('click', function() {
        populateForm(stock);
        stockDropdown.style.display = 'none';  // Optionally hide the dropdown after a selection is made
      });
      stockDropdown.appendChild(option);
    }
  });
}

function populateForm(stock) {
  document.getElementById('stockName').value = stock.name;
  document.getElementById('stockCode').value = stock.code;
  document.getElementById('stockPrice').value = stock.price;

  // Show the form if it is hidden
  document.getElementById('stockForm').style.display = 'block';
}

function addStock() {
  // Your logic for adding the stock goes here.
}