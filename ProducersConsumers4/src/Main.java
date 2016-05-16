import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
	public static void main(String args[]){
			ArrayList<Producer>producers;
			ArrayList<Consumer>consumers;
			Buffer buffer = new Buffer(1000);
			BufferProxy proxy;
			try {
				System.setOut(new PrintStream(new FileOutputStream("test.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			proxy = new BufferProxy(buffer);
			producers = new ArrayList<Producer>();
			consumers = new ArrayList<Consumer>();
			int j=1000;
			for(int i=0;i<j;i++){
				Producer p = new Producer(proxy,500);
				producers.add(p);
				Consumer c = new Consumer(proxy,500);
				consumers.add(c);
			}
			for(int i=0;i<j;i++){
				producers.get(i).start();
				consumers.get(i).start();
			}
		
		
			try {
				System.setOut(new PrintStream(new FileOutputStream("result.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try (BufferedReader reader = Files.newBufferedReader(Paths.get("test.txt"))) {
			    String line = null;
			    int i=2000;
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
