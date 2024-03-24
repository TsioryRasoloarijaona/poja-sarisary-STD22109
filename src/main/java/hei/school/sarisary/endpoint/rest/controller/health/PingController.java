package hei.school.sarisary.endpoint.rest.controller.health;

import hei.school.sarisary.PojaGenerated;
import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.repository.DummyRepository;
import hei.school.sarisary.repository.DummyUuidRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.CompletableFuture;

@PojaGenerated
@RestController
@AllArgsConstructor
public class PingController {

  DummyRepository dummyRepository;
  DummyUuidRepository dummyUuidRepository;
  BucketComponent bucketComponent;
  public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
  public static final ResponseEntity<String> KO =
          new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }

  @PostMapping(value = "/black/{id}",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
  public String toBlackAndWhite(@RequestBody MultipartFile img, @PathVariable String id) {
    CompletableFuture<Void> uploadTask = CompletableFuture.runAsync(() ->
    {
      try {
        File file = File.createTempFile("temp", null);
        img.transferTo(file);
        bucketComponent.upload(file, id+"original.png");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
      return "uploaded";
  }
}
