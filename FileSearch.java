import java.io.*;
import java.util.*;
import java.nio.channels.FileChannel;

public class FileSearch{
	private static String[] fileTypes=null;

	public static void main(String[] args){
		File fileOut = new File(args[1]);
		if(!fileOut.exists()){
			try{
				fileOut.mkdirs();
			}catch(Exception e){

			}
		}
		fileTypes=new String[args.length-2];
		for(int i=2;i<args.length;i++){
			fileTypes[i-2]=new String(args[i]);
		}
		scan(args[0],fileOut);
	}
	private static boolean FileType(String filename){
		for(String str:fileTypes){
			if(filename.endsWith(str)){
				return true;
			}
		}
		return false;
	}
	public static void scan(String pathname,File target){
		File file=new File(pathname);
		
		if(file.isDirectory()){
			String[] paths=file.list();
			if(paths!=null){
				for(String path:paths){
					scan(file.getAbsolutePath()+"\\"+path,target);
				}
			}
		}else{
			if(FileType(file.getName())){
				
				File f=new File(target.getAbsolutePath()+"\\"+file.getName());
				System.out.println(f.getAbsolutePath());
				nioTransferCopy(file,f);
			}
		}
	}
	private static void nioTransferCopy(File source, File target) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally { 
	    	try {
				in.close();
				inStream.close();
		    	out.close();
		    	outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    }  
	}  
}