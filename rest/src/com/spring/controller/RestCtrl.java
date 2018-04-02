package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spring.model.BeanCopyModel;
import com.spring.model.DbProps;
import com.spring.model.EndResult;
import com.spring.model.Login;
import com.spring.service.Scheduler;
import com.spring.service.TransactionsServ;
import com.spring.service.UserService;

@RestController
@SessionAttributes("roles")
public class RestCtrl {

	// private final static Logger LOGGER =
	// LoggerFactory.getLogger(RestCtrl.class);

//	private static final String EXTERNAL_FILE_PATH = "D:/Logs/";
	
	@Autowired
	Scheduler scheduler;

	@Autowired
	UserService userService;
	@Autowired
	TransactionsServ transServ;

	EndResult endResult;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<EndResult> test() {
		endResult = new EndResult();
		endResult.setStatus("success");
		return new ResponseEntity<EndResult>(endResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EndResult> login(@RequestBody Login login) {
		endResult = transServ.userLogin(login);
		return new ResponseEntity<EndResult>(endResult, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/beancopy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EndResult> beanCopy(@RequestBody BeanCopyModel beanCopyModel) {
		endResult = transServ.beanCopy(beanCopyModel);
		return new ResponseEntity<EndResult>(endResult, HttpStatus.OK);
	}

	/*@RequestMapping(value = "/download/{file:.+}", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @PathVariable("file") String type) throws IOException {

		File file = new File(EXTERNAL_FILE_PATH + type);

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);

		
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

		
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}*/

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public ResponseEntity<Object> cache() {
		List<DbProps> result = userService.getMapFromDb();
		if (result == null || result.size() == 0) {
			return new ResponseEntity<Object>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<String> refresh() {
		String result = userService.refreshCache();
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public ResponseEntity<Object> getMap() {
		Object result = userService.getParamMap();
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/constr", method = RequestMethod.GET)
	public ResponseEntity<Object> getConstr() {
		Object result = transServ.getProps();
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/aDenied", method = RequestMethod.POST)
	public ResponseEntity<EndResult> aDenied() {
		endResult = new EndResult();
		endResult.setStatus("fail");
		endResult.setMessage("You are not authorized to use this resource.");
		return new ResponseEntity<EndResult>(endResult, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/reschedule", method = RequestMethod.GET)
	public ResponseEntity<String> schedule() {
		scheduler.doReschedule();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}