package helloworld

class HelloController {

	SearchService searchService

    def index() { 
		render "Hello World!!"
	}

	def testview() {
		render(view: 'testview')
	}

	def weather() {
		if (null != params.zip) {
			//check if search row exists in table
			long counter = searchService.saveSearchData(params.zip)
//			if (null != session.getAttribute(params.zip)){
//				//PUT -- update search count of existing search
//				def num = session.getAttribute(params.zip);
//				num = num + 1;
//				session.setAttribute(params.zip, num);
//			} else {
//				//POST -- add new row to table
//				session.setAttribute(params.zip, 1);
//			}
			//Display the current count for the corresponding search query
			session.setAttribute("tobedisplayed", counter);
		}
		render (view: 'weatherpage');
	}

	def clrData() {
//		session.invalidate();
		searchService.clearAllHistory();
		redirect (action: 'weather');
	}
}
