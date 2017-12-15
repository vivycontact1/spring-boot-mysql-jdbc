package com.vivy.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vivy.models.FileModel;
import com.vivy.services.IFileService;

@RestController
public class FileController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	 @Autowired
	 ServletContext context;
	
	@Autowired
	IFileService fileService;
	
	
	@RequestMapping(value="/file",method = RequestMethod.POST)
	public int add(@RequestBody FileModel model){
		return fileService.save(model);
	}
	
	@RequestMapping(value="/file/delete",method = RequestMethod.POST)
	public int delete(@RequestBody int id){
		return fileService.delete(id);
	}
	
	@RequestMapping(value="/files",method = RequestMethod.GET)
	public List<FileModel> findAll(){
		return fileService.findAll();
	}
	
	@RequestMapping("/download/{fileName}")
	public ResponseEntity<InputStreamResource> downloadFileById(@PathVariable("fileName") String fileName) throws IOException {

	      File file = new File("FILE_PATH"+File.separator+fileName);
	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	      return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                  "attachment;filename=" + file.getName())
	            //.contentType(MediaType.APPLICATION_PDF)
	            .contentLength(file.length())
	            .body(resource);
	   }
	
	@RequestMapping("/files/{id}")
	public FileModel findFileById(@PathVariable("id") int id){
		return fileService.findFileById(id);
	}
	
	 @RequestMapping(value = "/fileupload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<Integer> upload(@RequestParam("file") MultipartFile inputFile, @RequestParam("capabilityId") int capabilityId) {
		 Integer result = -1;
		 FileModel fileModel = new FileModel();
		 HttpHeaders headers = new HttpHeaders();
		 if (!inputFile.isEmpty()) {
			try {
				String originalFilename = inputFile.getOriginalFilename();
				int index = originalFilename.indexOf(".");
				while (index >= 0) {
				    System.out.println(index);
				    int i = originalFilename.indexOf(".", index + 1);
				    if(i==-1){
				    	break;
				    }
				    index = i;
				}
				String fileName = originalFilename.substring(0,index);
				String extension = originalFilename.substring(index,originalFilename.length());
				File destinationFile = new File(System.getProperty("user.home") + File.separator +"showtime_files" +File.separator +fileName+"_"+System.currentTimeMillis()+extension);
				inputFile.transferTo(destinationFile);
				fileModel.setFileName(destinationFile.getName());
				fileModel.setFileSize(inputFile.getSize());
				fileModel.setName(originalFilename);
				fileModel.setType(extension.substring(1));
				headers.add("File Uploaded Successfully - ", originalFilename);
				result = fileService.save(fileModel);
				return new ResponseEntity<Integer>(result, headers, HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
			}
		 } else {
			return new ResponseEntity<Integer>(result,HttpStatus.BAD_REQUEST);
		 }
	}

}
