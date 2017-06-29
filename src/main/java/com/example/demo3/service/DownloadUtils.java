package com.example.demo3.service;

import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by IntelliJ IDEA.
 * User: xinqi
 * Date: 2017/6/29
 * Time: 15:00
 */
@Service
public class DownloadUtils {
    private static int BUF_SIZE = 1024 * 1024;

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println(end - begin +"ms");
    }

    /**
     * 下载文件
     * @param filePath 文件根路径
     * @param response
     * @param fileName 文件名称
     * @return
     */
    public static boolean download(String filePath, HttpServletResponse response,String fileName){
        RandomAccessFile rFile = null;
        BufferedOutputStream bos = null;
        try{
            //获取文件
            rFile = new RandomAccessFile(filePath + fileName,"r");
            //设置相应信息，包括对文件名称的转码等信息。
            String headerValue = "attachment;";
            headerValue += " filename=\"" + encodeURIComponent(fileName) +"\";";
            headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);
            response.setHeader("Content-Disposition", headerValue);
            response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Length", rFile.length()+"");
            //输出文件
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[BUF_SIZE];
            while (rFile.read(buff) != -1){
                bos.write(buff);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                rFile.close();
                bos.close();
            } catch (IOException e) {
            }
        }
        return true;
    }

    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
