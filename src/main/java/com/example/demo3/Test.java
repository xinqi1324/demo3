package com.example.demo3;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by IntelliJ IDEA.
 * User: xinqi
 * Date: 2017/6/27
 * Time: 10:17
 */
public class Test {

    public static void main(String[] args) {
        testIo();
    }
    public static void testIo(){
        long beginTime = System.currentTimeMillis();
        nioTest();
        long useTime = System.currentTimeMillis() - beginTime;
        System.out.println("nio use" + useTime + "ms" );
        beginTime = System.currentTimeMillis();
        ioTest();
        useTime = System.currentTimeMillis() - beginTime;
        System.out.println("io use" + useTime + "ms" );
        nioTest2();
        useTime = System.currentTimeMillis() - beginTime;
        System.out.println("buffer nio use" + useTime + "ms" );
    }
    public static void nioTest(){
        FileInputStream fin = null;
        FileChannel fc = null;
        FileChannel foc = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream("F:\\迅雷下载\\[电影天堂www.dy2018.com]mghe行动BD国语中字.rmvb");
            fout = new FileOutputStream("E:\\3.3.rmvb");
            fc = fin.getChannel();
            foc = fout.getChannel();
            fc.transferTo(0,fc.size(),foc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                fc.close();
                foc.close();
                fout.close();
            } catch (IOException e) {
            }
        }
    }
    public static void nioTest2(){
        RandomAccessFile fin = null;
        RandomAccessFile fout = null;
        try {
            fin = new RandomAccessFile("F:\\迅雷下载\\[电影天堂www.dy2018.com]mghe行动BD国语中字.rmvb","r");
            fout = new RandomAccessFile("E:\\4.4.rmvb","rw");
            byte[] buf = new byte[1024*10];
            int i = fin.read(buf);
            while (i > 0){
                if(i == buf.length){
                    fout.write(buf);
                }else{
                    fout.write(buf,0,i);
                }
                i = fin.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                fout.close();
            } catch (IOException e) {
            }
        }
    }
    public static void ioTest(){
        RandomAccessFile fin = null;

        RandomAccessFile fout = null;
        try{
            fin = new RandomAccessFile("F:\\迅雷下载\\[电影天堂www.dy2018.com]mghe行动BD国语中字.rmvb","r");
            fout = new RandomAccessFile("E:\\2.2.rmvb","rw");
            byte[] bytes = new byte[1024];
            while (fin.read(bytes) != -1){
                fout.write(bytes);
            }
        }catch (IOException e){

        }finally {
            try {
                fin.close();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
