package site.joshua.acs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.joshua.acs.domain.MinutesFile;
import site.joshua.acs.repository.MinutesFileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinutesFileService {

    private final AmazonS3 amazonS3;

    private final MinutesFileRepository minutesFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveUploadFile(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFileName = multipartFile.getOriginalFilename();
        // 파일 확장자 추출
        int index = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "test/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileUrl = amazonS3.getUrl(bucket, key).toString();
        MinutesFile minutesFile = new MinutesFile(originalFileName, storeFileUrl);
        minutesFileRepository.save(minutesFile);
    }

}
