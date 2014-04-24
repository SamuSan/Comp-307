package comp307_a2_part3_jgapvrs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Converter {

	public static void main(String[] args) {
		ArrayList<String> lines = new ArrayList<String>();
		String file = args[0];
		Scanner scan = new Scanner(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(file)));
		while (scan.hasNextLine()) {
			String[] s = scan.nextLine().split("\t");
			if(s[9].equals("2")){
				s[9] = "-1";
			}
			else if (s[9].equals("4")){
				s[9] = "1";
			}
			String x="";
			for (String string : s) {
				x+=string+"\t";
			}
			lines.add(x);
		}
		
		try {
			FileWriter write = new FileWriter(new File("BCStrippedWiderCast.txt"));
			
			for (String string : lines) {
				write.write(string+"\n");
			}
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
