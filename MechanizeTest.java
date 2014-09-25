import java.util.Map;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.Resource;
import com.gistlabs.mechanize.cookie.Cookie;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.form.Form;

public class MechanizeTest {
	public static void main(String[] args) throws Exception {
		
		/*
		 * Loading the webpage took 1492 milliseconds
		 * Parsing took 496 milliseconds
		 */
		
		Student student = new Student(M.USERNAME, M.PASSWORD);
		
		for(Class c: student.getClasses()) {
			System.out.println(c.getName());
			
			for(WeightCategory cat: c.getWeights()) {
				System.out.println(cat.getWeight() + ": " + cat.getTotalReceivedPoints() + "/" + cat.getTotalPossiblePoints());
			}
			
			System.out.println();
		}
		
	}
}
