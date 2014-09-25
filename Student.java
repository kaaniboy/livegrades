import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gistlabs.mechanize.document.html.form.Form;


public class Student {
	private String name;
	private ArrayList<Class> classes = new ArrayList<Class>();

	public Student(String username, String password) {

		com.gistlabs.mechanize.document.Document loginPage = M.agent.get(M.BASE_URL + "Login_Student_PXP.aspx");
		Form loginForm = loginPage.forms().get(0);

		loginForm.get("username").set(username);
		loginForm.get("password").set(password);

		loginForm.submit();

		// M.agent.cookies().addNewCookie(M.COOKIE_NAME, M.COOKIE_VALUE,
		// M.COOKIE_DOMAIN);

		com.gistlabs.mechanize.document.Document classesPage = M.agent.get(M.BASE_URL
				+ "PXP_Gradebook.aspx?AGU=0");
		
		loadClasses(classesPage.asString());
	}
	
	private void loadClasses(String classesPageHTML) {
		Document classesSoup = Jsoup.parse(classesPageHTML);

		this.name = classesSoup.select(".UserHead > span").text().split(",")[1]
				.substring(1);

		Elements classesList = classesSoup.select(".altrow1,.altrow2");

		for (Element e : classesList) {
			com.gistlabs.mechanize.document.Document classPage = M.agent
					.get(M.BASE_URL + e.child(0).child(0).attr("href"));

			String className = e.child(1).child(0).html();
			String roomName = e.child(3).child(0).text();
			String teacherName = e.child(4).child(0).child(0).text();
			
			String gradeString = e.child(5).child(0).text();
			gradeString = gradeString.replaceAll("[^\\d.]", "");
			
			double grade = Double.parseDouble(gradeString);

			Class newClass = new Class(className, roomName, teacherName, grade,
					classPage.asString());
			
			classes.add(newClass);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Class> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<Class> classes) {
		this.classes = classes;
	}
}
