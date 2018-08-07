package wdk.option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
 
public class DeliverFile2 {
	//private volatile
	public static void main(String[] args) {
        //cut();
       // merge();
    }
    //拆分文件
    public static void cut() {
        File file = new File("F:\\LinuxSys\\-bin-DVD1.iso");
        long lon = file.length() / 10L + 1L;//使文件字节数+1，保证取到所有的字节
        try {
            RandomAccessFile raf1 = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[1024*1024];//值设置越小，则各个文件的字节数越接近平均值，但效率会降低，这里折中，取1024
            int len = -1;
            for (int i = 0; i < 10; i++) {
                String name = "F:\\CentOS-6.5-x86_64-bin-DVD_" + i + ".iso";
                File file2 = new File(name);
                RandomAccessFile raf2 = new RandomAccessFile(file2, "rw");
 
                while ((len = raf1.read(bytes)) != -1){//读到文件末尾时，len返回-1，结束循环
                    raf2.write(bytes, 0, len);
                    if (raf2.length() > lon)//当生成的新文件字节数大于lon时，结束循环
                        break;
                }
                raf2.close();
            }
            raf1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    //合并文件
    public static void merge(List<File> list) {
    	File f = list.get(0);
    	String absolutePath = f.getAbsolutePath();
    	String name = f.getName();
    	String[] s = name.split("\\.");
    	name.substring(name.lastIndexOf(".")+1);      
    	//name.split(".")[0]+"合成版";
    	absolutePath = absolutePath.replace(name,s[0]+"合成版_1."+name.substring(name.lastIndexOf(".")+1));
    	File file = new File(absolutePath);
        try {
            RandomAccessFile target = new RandomAccessFile(file, "rw");
            for (int i = 0; i < list.size(); i++) {
                File file2 = list.get(i);
                RandomAccessFile src = new RandomAccessFile(file2, "r");
                byte[] bytes = new byte[1024*1024];//每次读取字节数
                int len = -1;
                while ((len = src.read(bytes)) != -1) {
                    target.write(bytes, 0, len);//循环赋值
                }
                src.close();
            }
            target.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
}