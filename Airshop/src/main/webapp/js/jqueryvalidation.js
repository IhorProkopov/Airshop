function Registration() {
	var res = true;
	var pattern = /^[a-zA-Z_-]{2,}$/;
	var text = $('#Surname').val();
	if (text == '' || text.length > 20 || !pattern.test(text)) {
		$('#surnameerror').show();
		res = false;
	} else {
		$('#surnameerror').hide();
	}

	text = $('#Name').val();
	if (text == '' || text.length > 20 || !pattern.test(text)) {
		$('#nameerror').show();
		res = false;
	} else {
		$('#nameerror').hide();
	}

	text = $('#Captcha').val();
	if (text == '') {
		$('#captError').show();
		res = false;
	} else {
		$('#captError').hide();
	}
	
	pattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;

	if (!pattern.test($('#Password').val())) {
		$('#passworderror').show();
		res = false;
	} else {
		$('#passworderror').hide();
	}

	if (!pattern.test($('#confirmPassword').val())) {
		$('#confirmpassworderror').show();
		res = false;
	} else {
		$('#confirmpassworderror').hide();
	}

	if ($('#confirmPassword').val() != $('#Password').val()) {
		$('#equalerror').show();	
		res = false;
	} else {
		$('#equalerror').hide();		
	}

	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if ($('#Email').val() == ''
			|| !re.test(document.getElementById('Email').value)) {
		$('#emailerror').show();
		res = false;
	} else {
		$('#emailerror').hide();
	}

	return res;

}