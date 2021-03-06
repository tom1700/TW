import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
	public static void main(String[]args){
		boolean calculate=true;
		if(!calculate){
			Monitor m = new Monitor(1000);
			try {
				System.setOut(new PrintStream(new FileOutputStream("test.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			for(int i=0;i<1000;i++){
				new Consumer(m,500).start();
				new Producer(m,500).start();
			}
		}
		else{
			try {
				System.setOut(new PrintStream(new FileOutputStream("result.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try (BufferedReader reader = Files.newBufferedReader(Paths.get("test.txt"))) {
			    String line = null;
			    int i=1000;
			    while ((line = reader.readLine()) != null) {
			    	String tmp[] = line.split(" ");
			    	BigInteger result=BigInteger.valueOf(0);
			    	for(int k=0;k<tmp.length;k++){
			    		result = result.add(BigInteger.valueOf(Integer.parseInt(tmp[k])));
			    	}
			    	result = result.divide(BigInteger.valueOf(tmp.length));
			        System.out.println(i + " " + result.toString());
			    	i++;
			    }
			} catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
		}
		
	}
}
