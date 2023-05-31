package site.joshua.acs.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MinutesFileForm {

    private List<MultipartFile> minutesFiles;
    private String year;
    private String month;
    private String day;

}
