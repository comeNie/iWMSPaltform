package com.huamengtong.wms.app.controller.main;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Edwin on 2016/12/9.
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void getTemplate(@RequestParam String type){
    }

    @RequestMapping("/files/{fileName}")
    public ResponseEntity download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ctxPath = request.getSession().getServletContext().getRealPath("/") + "/download/template/";
        String downLoadPath = ctxPath + fileName + ".xlsx";
        try {
            File file= new File(downLoadPath);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\""+new String(fileName.getBytes(), "ISO-8859-1")+".xlsx"+"\"");
            Long fileSize = file.length();
            response.setContentLength(fileSize.intValue());
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
