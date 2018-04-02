package com.spring.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.model.FileDownload;

@Controller
public class WebController {

	private static final String EXTERNAL_FILE_PATH = "D:/Logs/";

	@RequestMapping(value = { "/download" }, method = RequestMethod.GET)
	public String getHomePage(ModelMap model) {
		FileDownload fileDownload = new FileDownload();
		model.put("fileDownload", fileDownload);
		return "welcome";
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, HttpServletRequest request, @Valid FileDownload fileDownload, BindingResult result,
			ModelMap model) throws IOException {
		
		File file = new File(EXTERNAL_FILE_PATH + fileDownload.getFile());
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
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}
