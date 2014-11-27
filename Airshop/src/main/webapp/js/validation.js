function Registration() {
	var res = true;
	var pattern = /^[a-zA-Z_-]{2,}$/;
	var text = document.getElementById('Surname').value;
	if (text == '' || text.length > 20 ||!pattern.test(text)) {		
			if(isHidden(document.getElementById('surnameerror'))){
				show(document.getElementById('surnameerror'));
			}
			res = false;	
			}	else{
				if(!isHidden(document.getElementById('surnameerror'))){
					hide(document.getElementById('surnameerror'));
				}
			}
	
	text = document.getElementById('Name').value;
	if (text == '' || text.length > 20 ||!pattern.test(text)) {		
		if(isHidden(document.getElementById('nameerror'))){
			show(document.getElementById('nameerror'));
		}
		res = false;	
		}	else{
			if(!isHidden(document.getElementById('nameerror'))){
				hide(document.getElementById('nameerror'));
			}
		}
	
	text = document.getElementById('Captcha').value;
	if (text == '') {
		show(document.getElementById('captError'));
		res = false;
	} else {
		hide(document.getElementById('captError'));
	}
	
	pattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;    
	
	if (!pattern.test(document.getElementById('Password').value)) {
		if(isHidden(document.getElementById('passworderror'))){
			show(document.getElementById('passworderror'));
		}		
		res = false;
	}else{
		if(!isHidden(document.getElementById('passworderror'))){
			hide(document.getElementById('passworderror'));
		}
	}
	
	if (!pattern.test(document.getElementById('confirmPassword').value)) {
		if(isHidden(document.getElementById('confirmpassworderror'))){
			show(document.getElementById('confirmpassworderror'));
		}		
		res = false;
	}else{
		if(!isHidden(document.getElementById('confirmpassworderror'))){
			hide(document.getElementById('confirmpassworderror'));
		}
	}

	if (document.getElementById('confirmPassword').value != document.getElementById('Password').value) {
		if(isHidden(document.getElementById('equalerror'))){
			show(document.getElementById('equalerror'));			
		}		
		res = false;
	}else{
		if(!isHidden(document.getElementById('equalerror'))){
			hide(document.getElementById('equalerror'));			
		}
	}
	
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (document.getElementById('Email').value == '' || !re.test(document.getElementById('Email').value)) {		
		if(isHidden(document.getElementById('emailerror'))){
			show(document.getElementById('emailerror'));
		}		
		res = false;
	}else{
		if(!isHidden(document.getElementById('emailerror'))){
			hide(document.getElementById('emailerror'));
		}
	}
	return res;
}

function hide(el) {
	el.style.display='none';
}

function isHidden(el) {
	return el.style.display === 'none';
}

function show(el) {
	el.style.display='block';
}
