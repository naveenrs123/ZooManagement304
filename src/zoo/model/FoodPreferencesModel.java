package zoo.model;

public class FoodPreferencesModel {
    private String Food_Type;
    private String Species;

    public FoodPreferencesModel(String food_Type, String species) {
        Food_Type = food_Type;
        Species = species;
    }

    public String getFood_Type() {
        return Food_Type;
    }

    public String getSpecies() {
        return Species;
    }
}
