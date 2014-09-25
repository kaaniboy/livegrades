public class Assignment {
	private String date;
	private String name;
	private String type;
	private String score;
	private String points;
	
	private double totalPossiblePoints;
	private double totalReceivedPoints;

	public Assignment(String date, String name, String type, String score, String points) {
		this.date = date;
		this.name = name;
		this.type = type;
		this.score = score;
		this.points = points;
		
		if(!points.contains("Possible")) {
			String[] split = points.split("/");
			
			totalReceivedPoints = Double.parseDouble(split[0]);
			totalPossiblePoints = Double.parseDouble(split[1]);
		}
	}

	public String getScore() {
		return score;
	}

	public String getDate() {
		return date;
	}

	public String getName() {
		return name;
	}


	public String getType() {
		return type;
	}


	public String getPoints() {
		return points;
	}
	
	public double getTotalPossiblePoints() {
		return totalPossiblePoints;
	}

	public double getTotalReceivedPoints() {
		return totalReceivedPoints;
	}
}
