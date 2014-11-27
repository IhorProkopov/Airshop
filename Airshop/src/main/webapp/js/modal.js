function showGoodsModal(img, name, price) {
	modalProductName
	$("#modalImg").attr('src', img);
	$("#modalProductName").text(name);
	$("#modalProductPrice").text(price);
}