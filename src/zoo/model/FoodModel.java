package zoo.model;

public class FoodModel {
    private String food_ID;
    private String type;
    private int Inventory_Amount;

    public FoodModel(String food_ID, String type, int inventory_Amount) {
        this.food_ID = food_ID;
        this.type = type;
        Inventory_Amount = inventory_Amount;
    }

    public String getFood_ID() {
        return food_ID;
    }

    public String getType() {
        return type;
    }

    public int getInventory_Amount() {
        return Inventory_Amount;
    }
}
