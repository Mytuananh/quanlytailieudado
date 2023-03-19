package com.dado.quanlytailieu.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.dado.quanlytailieu.command.ImageUpdateCommand;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.dto.UploadImageResponse;
import com.dado.quanlytailieu.repository.ConstructionRepository;
import com.dado.quanlytailieu.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Value("${extern.resources.path}")
    private String path;

    @Autowired
    ImageService imageService;

    @Autowired
    ConstructionRepository constructionRepository;

    @GetMapping("/download")
    public ResponseEntity downloadImage(@RequestParam("id") String id) throws Exception {
        var image = imageService.downloadImage(id);
        if(image == null){
            return ResponseEntity.badRequest().body("Resource of image error");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"");
        return new ResponseEntity(image, headers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu file vào server
            String fileName = file.getOriginalFilename();
            File targetFile = new File(path + "/" + fileName);
            file.transferTo(targetFile);

            // Tạo đường dẫn URL cho file đã lưu
            String fileUrl = "http://localhost:8080/uploads/" + fileName;

            // Trả về URL của file đã lưu
            return ResponseEntity.ok().body(new UploadImageResponse(fileUrl, fileName));
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi lưu file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/upload/construction/{constructionId}")
    public ResponseEntity<Object> uploadImageByConstructionId(@RequestParam("file") MultipartFile file, @PathVariable Long constructionId) {
        try {
            // Lưu file vào server
            System.out.println("handleFileUpload");
            var cons = constructionRepository.findById(constructionId).orElseThrow(() -> new RuntimeException("Khong tim thay ConstructionId"));
            imageService.storeImageForConstruction(new MultipartFile[]{file}, cons);
            String fileName = file.getOriginalFilename();

            // Tạo đường dẫn URL cho file đã lưu
            String fileUrl = "http://localhost:8080/api/images/preview/" + fileName;

            // Trả về URL của file đã lưu
            return ResponseEntity.ok().body(fileUrl);
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi lưu file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseDto deleteImageById(@RequestParam(name = "id", required = true) String id) throws Exception {
        var image = imageService.deleteImageById(id);
        return ResponseDto.builder()
                .message(image.getMessage())
                .httpCode(image.getHttpCode())
                .body(image.getBody()).build();
    }

    @PutMapping("/update")
    public ResponseDto updateImageById(
            @ModelAttribute ImageUpdateCommand command) throws Exception {
        var image = imageService.updateImage(command.getId()
                , command.getCreatedTime()
                , command.getCreatedUser()
                , command.getFile()
                , command.getFileId());

        return ResponseDto.builder()
                .message(image.getMessage())
                .httpCode(image.getHttpCode())
                .body(image.getBody()).build();
    }

    @GetMapping(value = "/preview/{imageName:.+}")
    public ResponseEntity<Resource> getPreviewFile(@PathVariable String imageName) throws MalformedURLException {
        Path filePath = Paths.get("uploads/files/", imageName);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            MediaType[] mediaTypes = { MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG };

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=" + imageName)
                        .contentLength(Files.size(filePath))
                        .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .contentType(Arrays.stream(mediaTypes)
                                .filter(mt -> mt.getSubtype().equalsIgnoreCase(FilenameUtils.getExtension(imageName)))
                                .findFirst().orElse(MediaType.IMAGE_PNG))
                        .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getImageById(@RequestParam(name = "id")String id){
//        var image = imageService.getImageById(id);
//        if(Objects.isNull(image)){
//            return ResponseEntity.badRequest().body("Image not exist");
//        }
//        return ResponseEntity.ok().body(image);
//    }

    @GetMapping("/construction/{constructionId}")
    public ResponseEntity<?> getImageByConstructionId(@PathVariable String constructionId) throws MalformedURLException {
        return ResponseEntity.ok().body(imageService.getListImageName(constructionId));
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = loadAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return null;
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    public Resource loadAsResource(String fileName) {
        try {
            Path file = Paths.get(path).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw   new RuntimeException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }
}
