package site.joshua.acs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.joshua.acs.domain.MinutesFile;
import site.joshua.acs.repository.MinutesFileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinutesFileService {

    private final AmazonS3 amazonS3;

    private final MinutesFileRepository minutesFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveUploadFile(List<MultipartFile> multipartFiles, String date) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            String originalFileName = multipartFile.getOriginalFilename();
            // 파일 확장자 추출
            int index = originalFileName.lastIndexOf(".");
            String ext = originalFileName.substring(index + 1);

            String storeFileName = UUID.randomUUID() + "." + ext;
            String keyName = "test/" + date + "/" + storeFileName;

            // AWS S3 에 넣는 작업. putObject 를 사용한다.
            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, keyName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            }

            // DB 에 저장하기 위한 작업. S3의 URL 을 가져와 객체에 저장시켜 넘긴다.
            String storeFileUrl = amazonS3.getUrl(bucket, keyName).toString();
            MinutesFile minutesFile = new MinutesFile();
            minutesFile.setMinutesFile(originalFileName, storeFileUrl, date);
            minutesFileRepository.save(minutesFile);
        }
    }

    public List<MinutesFile> findMinutesFiles() {
        return minutesFileRepository.findAll();
    }

    @Transactional
    public String deleteMinutesFile(Long minutesFileId) {

        String result = "success";
        MinutesFile minutesFile = minutesFileRepository.fineOne(minutesFileId);

        // AWS S3 에서 파일 삭제. deleteObject 를 사용한다.
        try {
            // DB 에 저장되어 있는 URL 을 분리해서 keyName을 만든다.
            String[] ext = minutesFile.getStoreFileName().split("/");
            String keyName = ext[3] + "/" + ext[4] + "/" + ext[5];
            boolean isObjectExist = amazonS3.doesObjectExist(bucket, keyName);
            if (isObjectExist) {
                amazonS3.deleteObject(bucket, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        // DB 에서 해당 id를 지닌 데이터 삭제.
        minutesFileRepository.delete(minutesFile);

        return result;
    }

    public ResponseEntity<byte[]> downLoadFile(Long minutesFileId) throws IOException {

        MinutesFile minutesFile = minutesFileRepository.fineOne(minutesFileId);
        String[] ext = minutesFile.getStoreFileName().split("/");
        String keyName = ext[3] + "/" + ext[4] + "/" + ext[5];

        S3Object object = amazonS3.getObject(new GetObjectRequest(bucket, keyName));
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(keyName, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

}
