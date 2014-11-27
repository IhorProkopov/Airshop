<input type="button" class="button radius small" onclick="addToCart()" value="Add to cart">
<script>
    function addToCart(){
            var xmlhttp = new XMLHttpRequest();
            console.log(document.getElementById('Email').value);
            xmlhttp.open('GET', '/Airshop/addtocart?product=' +document.getElementById('value').value, true);
            xmlhttp.onreadystatechange = function() {
              if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
               var Data = JSON.parse(xmlhttp.responseText);
                     var productCount = Data.count;
                     $("#productSpan").text(''+productCount);
              }
            };
            xmlhttp.send(null);
        }
</script>