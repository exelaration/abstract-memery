package com.exelaration.abstractmemery.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exelaration.abstractmemery.domains.Image;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {

	public static final String uploadingDir = "/app/src/main/resources/images/";

	@PostMapping("/")
	public Image uploadData(@RequestParam("file") MultipartFile file) throws Exception {

		if (file == null) {
			throw new RuntimeException("You must select the a file for uploading");
		}
		Image image = new Image(null, null);
		try {
			//saves the image 
			byte[] bytes = file.getBytes();
			String fileName = file.getOriginalFilename();
			Path path = Paths.get(uploadingDir + fileName);
			Files.write(path, bytes);

			String fileData = Base64.encodeBase64String(bytes);
			image.setFileData(fileData);

        } catch (IOException e) {
			e.printStackTrace();
        }
		return image;
	}
}