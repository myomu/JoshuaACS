package site.joshua.acs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.joshua.acs.domain.MinutesFile;
import site.joshua.acs.form.MinutesFileForm;
import site.joshua.acs.service.MinutesFileService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MinutesController {

    private final MinutesFileService minutesFileService;

    /**
     * 회의록 화면 GET 요청
     */
    @GetMapping("/minutes")
    public String minutes(@ModelAttribute MinutesFileForm form, Model model) {

        List<MinutesFile> minutesFiles = minutesFileService.findMinutesFiles();
        model.addAttribute("minutesFiles", minutesFiles);

        return "minutes/minutes";
    }

    /**
     * 회의록 추가
     */
    @PostMapping("/minutes/new")
    public String uploadFile(@ModelAttribute MinutesFileForm form) throws IOException {

        log.info("multipartFile={}", form.toString());
        String date = form.getYear() + "-" + form.getMonth() + "-" + form.getDay();
        minutesFileService.saveUploadFile(form.getMinutesFiles(), date);

        return "redirect:/minutes";
    }

    /**
     * 회의록 삭제
     */
    @PostMapping("/minutes/{minutesFileId}/delete")
    public String deleteFile(@PathVariable("minutesFileId") Long minutesFileId) {
        minutesFileService.deleteMinutesFile(minutesFileId);

        return "redirect:/minutes";
    }

    /**
     * 회의록 다운로드
     */
    @GetMapping("/minutes/{minutesFileId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("minutesFileId") Long minutesFileId) throws IOException {
        log.info("minutesId={}", minutesFileId);

        return minutesFileService.downLoadFile(minutesFileId);
    }
}
