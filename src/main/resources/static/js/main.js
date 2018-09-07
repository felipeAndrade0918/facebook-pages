function welcome() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText) {
				var pages = JSON.parse(this.responseText),
					pageList = document.getElementById('pages');
				
				for (var i = 0; i < pages.length; i++) {
					var pageItem = document.createElement('li');
					pageItem.innerHTML = pages[i];
					
					pageList.appendChild(pageItem);
				}
				document.getElementById('loginButton').classList.add('hidden');
			} else {
				document.getElementById('loginButton').classList.remove('hidden');
			}
		}
	};
	
	request.open('GET', 'oauth/pages', true);
	request.send();
}