package order;

import menu.MenuItem;
public class OrderLine {
	
	 private MenuItem item;
	 private int quantity;
	 
	 public OrderLine(MenuItem item) {
		    this(item, 1);
		}
	 public OrderLine( MenuItem item,int quantity ) {
		 this.quantity=quantity;
		 this.item=item;
	 }
	 
	 public double getLineTotal() {
			return item.getPrice() * quantity;
		}
}
