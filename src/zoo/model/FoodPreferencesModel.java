package zoo.model;

public class FoodPreferencesModel {
    private String Food_Type;
    private Species Species;

    public FoodPreferencesModel(String food_Type, Species species) {
        Food_Type = food_Type;
        Species = species;
    }

    public String getFood_Type() {
        return Food_Type;
    }

    public Species getSpecies() {
        return Species;
    }
}
