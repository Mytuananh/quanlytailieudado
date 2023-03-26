package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.entity.CongTrinh;
import com.dado.quanlytailieu.entity.Image;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ImageService {

    private Path fileStorageLocation = null;

    @Value("${extern.resources.path.image}")
    private String pathImage;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    CongTrinhRepository congTrinhRepository;

    @Autowired
    public void ImageService(Environment env) {
        this.fileStorageLocation = Paths.get(pathImage)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<Image> storeImageForConstruction(List<MultipartFile> files) throws Exception {
        List<Image> images = new ArrayList<>();
        Set<String> set = new HashSet<>();
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file: files) {
            if(file.getSize() <= 0){
                return null;
            }else if (!set.add(file.getOriginalFilename())){
                return null;
            } else {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setType(file.getContentType());
//                image.setCreatedUser("Nam");
                imageRepository.save(image);
                images.add(image);

                // Normalize file name
                String fileName = file.getOriginalFilename();
                try {
                    // Check if the filename contains invalid characters
                    if (fileName.contains("..")) {
                        return null;
                    }

                    Path targetLocation = this.fileStorageLocation.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    fileNames.add(fileName);
                } catch (IOException ex) {
                    return null;
                }
            }
        }
        return imageRepository.saveAll(images);
    }

    public ResponseEntity<Resource> getPreviewFile(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Not found imageId: " + imageId));
        Path filePath = Paths.get(pathImage, image.getFileName());

        try {
            Resource resource = new UrlResource(filePath.toUri());
            MediaType[] mediaTypes = { MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG };

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=" + image.getFileName())
                        .contentLength(Files.size(filePath))
                        .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .contentType(Arrays.stream(mediaTypes)
                                .filter(mt -> mt.getSubtype().equalsIgnoreCase(FilenameUtils.getExtension(image.getFileName())))
                                .findFirst().orElse(MediaType.IMAGE_PNG))
                        .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
}
