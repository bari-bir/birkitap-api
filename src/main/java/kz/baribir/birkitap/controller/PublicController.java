package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UploadDownloadUtil uploadDownloadUtil;

    @RequestMapping("/get_resource")
    public void get_resource(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {
        if (!name.startsWith("public/"))
            throw new RuntimeException("无权访问非public目录!");
        uploadDownloadUtil.download_resource(response, name);
    }
}
