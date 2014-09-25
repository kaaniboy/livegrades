public class WeightCategory {
	private String category;
	private double weight;
	private double totalReceivedPoints;
	private double totalPossiblePoints;

	public WeightCategory(String category, double weight, double totalReceivedPoints, double totalPossiblePoints) {
		this.category = category;
		this.weight = weight;
		this.totalReceivedPoints = totalReceivedPoints;
		this.totalPossiblePoints = totalPossiblePoints;
	}

	public String getCategory() {
		return category;
	}

	public double getWeight() {
		return weight;
	}

	public double getTotalReceivedPoints() {
		return totalReceivedPoints;
	}

	public double getTotalPossiblePoints() {
		return totalPossiblePoints;
	}
	
	
}
