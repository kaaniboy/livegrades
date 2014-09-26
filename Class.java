import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Class {
	private String name;
	private String teacher;
	private double grade;
	private String room;

	private double totalPossiblePoints;
	private double totalReceivedPoints;

	private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	private ArrayList<WeightCategory> weights = new ArrayList<WeightCategory>();

	public Class(String name, String room, String teacher, double grade,
			String classPageHTML) {
		this.name = name;
		this.room = room;
		this.teacher = teacher;
		this.grade = grade;

		Document classesSoup = Jsoup.parse(classPageHTML);

		Element assignmentsList = null;

		if (classesSoup.select(".info_tbl").size() == 3) {
			assignmentsList = classesSoup.select(".info_tbl").get(1);

			loadWeights(classesSoup.select(".info_tbl").get(0));
		} else {
			assignmentsList = classesSoup.select(".info_tbl").get(0);
		}

		for (Element e : assignmentsList.select(".altrow1,.altrow2")) {

			String date = e.child(0).child(0).text();
			String assignmentName = e.child(1).child(0).text();
			String type = e.child(2).child(0).text();
			String score = e.child(4).child(0).text();
			String points = e.child(6).child(0).text();

			Assignment assignment = new Assignment(date, assignmentName, type,
					score, points);

			assignments.add(assignment);
		}

		parseClassPoints();
	}

	private void loadWeights(Element weightsTable) {
		for (Element e : weightsTable.select(".altrow1,.altrow2")) {
			String categoryTitle = e.select("td").first().html().toString();
			String categoryPercentage = e.select("td").get(1).html().toString();

			double receivedPoints = Double.parseDouble(e.select("td").get(2)
					.html().toString());
			double totalPoints = Double.parseDouble(e.select("td").get(3)
					.html().toString());
			Double parsedPercentage = .01 * Double
					.parseDouble(categoryPercentage.replace("%", ""));

			WeightCategory weight = new WeightCategory(categoryTitle,
					parsedPercentage, receivedPoints, totalPoints);
			weights.add(weight);
		}
	}

	private void parseClassPoints() {
		for (Assignment a : assignments) {
			totalPossiblePoints += a.getTotalPossiblePoints();
			totalReceivedPoints += a.getTotalReceivedPoints();
		}
	}

	public double predictGrade(String category, double pointsReceived,
			double pointsPossible) {
		WeightCategory selectedCategory = null;
		double predictedGrade = 0;

		for (WeightCategory c : weights) {
			if (c.getCategory().equals(category)) {
				selectedCategory = c;
			} else {
				predictedGrade += (c.getTotalReceivedPoints() / c
						.getTotalPossiblePoints()) * c.getWeight();
			}
		}
		
		if(selectedCategory == null) return -1;

		predictedGrade += ((selectedCategory.getTotalReceivedPoints() + pointsReceived) / (selectedCategory
				.getTotalPossiblePoints() + pointsPossible))
				* selectedCategory.getWeight();
		
		return predictedGrade * 100;

	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public double getGrade() {
		return grade;
	}

	public String getRoom() {
		return room;
	}

	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}

	public ArrayList<WeightCategory> getWeights() {
		return weights;
	}

	public double getTotalPossiblePoints() {
		return totalPossiblePoints;
	}

	public double getTotalReceivedPoints() {
		return totalReceivedPoints;
	}

}
