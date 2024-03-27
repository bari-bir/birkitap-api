package kz.baribir.birkitap.components;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.util.TimeUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class UploadDownloadUtil {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Value("${file.upload.maxsize}")
    private long upfilemaxsize;

    @Value("${file.upload.suffix}")
    private String filesuffix;

    public SaveResult save_multipart(MultipartFile file, String folder_path, HttpServletRequest request) {
        SaveResult result = new SaveResult();
        File folder = new File(uploadDir + "/" + folder_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String oldName = file.getOriginalFilename();
        String suffx = oldName.substring(oldName.lastIndexOf(".")).toLowerCase().trim();
        if (file.getSize() >= upfilemaxsize) {
            result.setSuccess(false);
            result.setErrMsg("File Max Size:" + upfilemaxsize + " bytes.");
            return result;
        }

        String[] suffixes = filesuffix.split("\\|");
        boolean found = false;
        for (String suf : suffixes)
            if (suf.equals(suffx)) {
                found = true;
                break;
            }
        if (!found) {
            result.setSuccess(false);
            result.setErrMsg("suffix " + suffx + " cant be upload!");
            return result;
        }

        String newName = TimeUtil.timeid() + "_" + UUID.randomUUID() + suffx;
        try {
            file.transferTo(new File(folder, newName));
            result.setSuccess(true);
            result.setFileId(folder_path + "/" + newName);
        } catch (IOException e) {
            result.setSuccess(false);
            result.setErrMsg(e.getMessage());
        }

        return result;

    }

    public void download_resource(HttpServletResponse response, String path) {
        download_resource(response, uploadDir + "/" + path, "application/octet-stream", path.replaceAll("/", "_"));

    }

    private void download_local(String path, HttpServletResponse response, String mime, String downname) {
        String real_path = path;
        File file = new File(real_path);
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }
        //response.reset();
        response.setContentType(mime);
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + downname);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[10240];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            response.setStatus(200);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);

        }

        response.setStatus(401);


    }


    public void download_resource(HttpServletResponse response, String path, String mime, String downname) {
        download_local(path, response, mime, downname);

    }

}
