package menu;

public class Meal extends MenuItem{
    private int extraToppings;
   
    public Meal(String name,double basePrice,int extraToppings){
    	super(name,basePrice);
    	this.extraToppings=extraToppings;
    }
    
    @Override
    public double getPrice() {
    	return (extraToppings * 1.5)+basePrice;
    	
    }

}