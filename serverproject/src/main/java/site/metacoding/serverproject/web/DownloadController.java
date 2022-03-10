package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import site.metacoding.serverproject.domain.Download;
import site.metacoding.serverproject.domain.DownloadRepository;

@RequiredArgsConstructor
@Controller
public class DownloadController {

    private final DownloadRepository downloadRepository;

    @GetMapping("/")
    public String main() {
        return "/main";
    }

    @GetMapping("/download")
    public String download(Model model) {

        // 1. URI로 다운로드
        String address = "http://3.38.254.72:5000/api/hospital?sidoCdNm=부산&sgguCdNm=부산사하구";
        RestTemplate rt = new RestTemplate();
        Download[] download = rt.getForObject(address, Download[].class);
        // System.out.println(download);
        List<Download> downloadList = Arrays.asList(download);
        // System.out.println("1.============================" +
        // downloadList.get(0).getAddr());
        // System.out.println("2.============================" +
        // downloadList.get(1).getAddr());
        // System.out.println("3.============================" +
        // downloadList.get(2).getAddr());

        // 2. DB에 saveAll() + model에 담기
        downloadRepository.saveAll(downloadList);
        model.addAttribute("lists", downloadRepository.findAll());
        // if (downloadList.equals(downloadCheckList)) {
        // return "list";
        // }

        // 3. 리턴
        return "list";
    }

}
