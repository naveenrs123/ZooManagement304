package zoo.model;

/**
 * The intent for this class is to update/store information about a single animal
 */
public class AnimalModel {
	private String AnimalID;
	private String Type;
	private char Sex;
	private String Species;
	private int Age;
	private String Name;
	private int PenNumber;
	private char AreaID;

	public AnimalModel(String animalID, String type, char sex, String species, int age, String name, int penNumber, char areaID) {
		AnimalID = animalID;
		Type = type;
		Sex = sex;
		Species = species;
		Age = age;
		Name = name;
		PenNumber = penNumber;
		AreaID = areaID;
	}

	public String getAnimalID() {
		return AnimalID;
	}

	public String getType() {
		return Type;
	}

	public char getSex() {
		return Sex;
	}

	public String getSpecies() {
		return Species;
	}

	public int getAge() {
		return Age;
	}

	public String getName() {
		return Name;
	}

	public int getPenNumber() {
		return PenNumber;
	}

	public char getAreaID() {
		return AreaID;
	}
}
