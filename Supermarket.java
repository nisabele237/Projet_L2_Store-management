 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 import java.util.Random;
 class Product{
 public String idPro ;
 public String name ;
 public int price ;
 public int quantity ;
 public String category ;
 public Supplier SupplierInfo;
 
 //Constructor
 public Product (String name,int price,int quantity,String category,Supplier sup){
     Random random = new Random();
 String c1=category.substring(0,1).toUpperCase();
  int randomNum = random.nextInt(9000) + 1000;
 String c2=name.substring(0,3).toUpperCase();
 this.idPro="2024"+c1+randomNum+c2;
 this.name=name;
 this.price=price;
 this.quantity=quantity;
 this.category=category;
 this.SupplierInfo=sup;
}

public void  updateProductPrice(int updatePrice){
  	this.price=updatePrice;
}	

public void  updateProductQuantity(int updateQuantity){
  	this.quantity=updateQuantity;
}

public void getProduct(){
 	System.out.println("ID: "+idPro+" Name: "+name+" Price: "+price+" Quantity: "+quantity+" Category: "+category+" Suppliername: "+SupplierInfo.name);
}
public String getSuppliername(){
    return SupplierInfo.name;
}

}
 abstract class Person{
 protected String idP;
 protected String name;
 protected String surname;
 protected String address;
 
 public Person(String name,String surname,String address){
     Random random = new Random();
	 String c1=address.substring(2,3).toUpperCase();
	 String c2=name.substring(0,2).toUpperCase();
	 String c3=surname.substring(0,2);
	  int randomNum = random.nextInt(3000) + 1000;
	 this.idP="2024"+randomNum+c1+c2+c3;
	 this.name=name;
	 this.surname=surname;
	 this.address=address;
}

public String getId(){
 	return idP;
 }
 
public String  getName(){
	return name;
}

public String getAddress(){
	return address;
}


}

 class Customer extends Person{
 	public Customer (String name,String surname,String address){
 		super(name,surname,address);
 	}
}

 class Supplier extends Person{
  	public Supplier (String name,String surname,String address){
 		super(name,surname,address);
 	}
}

 class Order{
 String idO;
 Customer customerInfo;
 Supplier SupplierInfo;
 List <Product> productList;
 String date;
 
 //Constructor
 public Order(Customer cust,Supplier sup,String date){
     Random random = new Random();
        String c1 = date.substring(5,7);
        String c2 = cust.idP.substring(0, 3).toUpperCase();
        String c3 = sup.name.substring(0, 3).toUpperCase();
         int randomNum = random.nextInt(7000) + 1000;
	 this.idO="2024"+c1+c2+randomNum+c3;
	 this.date=date;
	 this.productList=new ArrayList<>();
	 this.customerInfo=cust;
 	
 }
 public void addProductOrder (Product P){
 productList.add(P);
 System.out.println("Adding of product "+P.idPro+" name: "+ P.name+" to the order.");
 }
  public void displayOrder() {
        System.out.println("Order ID: " + idO);
        System.out.println("Customer: " + customerInfo.getName());
        System.out.println("Supplier: "+SupplierInfo.getName());
        System.out.println("Date: " + date);
        System.out.println("Products in the Order:");
        for (Product product : productList) {
            System.out.println("- " + product.name + " (ID: " + product.idPro + ", Qty: " + product.quantity +")");
        }
    }
    //Methods for the calculation of the complete price of the order
    public int orderPrice(){
    int sum=0;
    for(Product prorder : productList){
    
  	sum+= prorder.price;
    }
    return sum;
    }
 
}
//Main management of the stock
 class SupermarketStock{
  private List<Order> orderList; //a list of Order to facilitate the whole management of orders
 private  List<Product> productList;// a list of Product to facilitate the whole management of products
  
  //Constructor
  public SupermarketStock(){
 this.orderList=new ArrayList<>();
 this.productList=new ArrayList<>();
  
  }
  //Adding a product in the inventory
  public void createProduct(Product P){
  	productList.add(P);
  }
  //Check if a product is in stock
  public int checkInventory(Product P){
  	if(P.quantity>0){
  		System.out.println("Product "+P.idPro + " in stock");
  		return P.quantity;
  	}
  	else{
  	System.out.println("Product "+P.idPro+"is out of stock");
  	return 0;
  	}
  	
  }
  public Object searchProduct(String id){
   	Product protest=null;
   for(Product pro:productList){
   	if(id.equals(pro.idPro)){
   		protest=pro;
   		break;
   		}
   }
   return protest;
  }
  
  public void restockProduct(String id,int quantity){
  Product protest=(Product)searchProduct(id);
  if(protest==null)
  	System.out.println("Missing Product in the stock");
  else{ 
  	protest.quantity+=quantity;
  	System.out.println("Restocking  for the product "+protest.idPro+" done successfully!");
  	
  }
  }
  //Reducing the stock when an order is placed
  public void processOrder(Product P,int quantity){
     P.quantity-=quantity;
     if(checkInventory(P)==0){
     	productList.remove(P);
     	}

  }
  
  //Methods for ordering  placement
 public void createOrder(Order ord) {
    boolean allProductsAvailable = true;

    // Vérifier la disponibilité des produits
    for (Product orderedProduct : ord.productList) {
        Product stockProduct = null;
	stockProduct=(Product)searchProduct(orderedProduct.idPro);
           

        // Si le produit n'existe pas ou la quantité est insuffisante
        if (stockProduct == null) {
            System.out.println("Missing product in the stock: " + orderedProduct.name);
            allProductsAvailable = false;
        } else if (orderedProduct.quantity > stockProduct.quantity) {
            System.out.println("Insufficient stock for product: " + stockProduct.name + 
                               " (Requested: " + orderedProduct.quantity + ", Available: " + stockProduct.quantity + ")");
            allProductsAvailable = false;
        }
    }

    // Si tous les produits sont disponibles, traiter la commande
    if (allProductsAvailable) {
        for (Product orderedProduct : ord.productList) {
            for (Product stockProduct : productList) {
                if (orderedProduct.idPro.equals(stockProduct.idPro)) {
                    	processOrder(stockProduct,orderedProduct.quantity);
                    	break;
                }
            }
        }
        orderList.add(ord);
        System.out.println("Order created successfully!");
        ord.displayOrder();
        System.out.println("Total price: "+ord.orderPrice());
    } else {
        System.out.println("Order creation failed due to stock issues.");
    }
}
public void searchProductByName(String name){
	Product protest=null;
	for(Product pro :productList){
		if(pro.name.equals(name)){
		protest=pro;
		protest.getProduct();
		break;
		}
	}
  if(protest==null)
  	System.out.println("Missing Product in the stock");
	
}
public void searchProductByCategory(String category){
	Product protest=null;
	for(Product pro :productList){
		if(pro.category.equals(category)){
		protest=pro;
		protest.getProduct();
		break;
		}
	}
  if(protest==null)
  	System.out.println("Missing Product in this category");
 	


}
public void searchProductBySupplier(String id){

	Product protest=null;
	for(Product pro :productList){
		if(pro.SupplierInfo.idP.equals(id)){
		protest=pro;
		protest.getProduct();
		break;
		}
	}
  if(protest==null)
  	System.out.println("Missing Product in the stock");
	
}

public void updatePriceProduct(String id,int price){
 	  	Product protest=null;
   for(Product pro:productList){
   	if(pro.idPro.equals(id))
   		protest=pro;
   }
   if(protest==null)
   	System.out.println("Missing Product in the stock");
   else{ 
   	protest.updateProductPrice(price);
   	System.out.println("Product "+protest.idPro+" successfully updated");
   }
}
 public void deleteProduct(String id){
 	  	Product protest=null;
   for(Product pro:productList){
   	if(pro.idPro.equals(id))
   		protest=pro;
   }
   if(protest==null)
   	System.out.println("Missing Product in the stock");
   else{ 
   	productList.remove(protest);
   	System.out.println("Product "+protest.idPro+" successfully deleted");
   }
 }
 
 public void cancelOrder(String id){
 Order ordtest=null;
         if (orderList.isEmpty()) {
            System.out.println("No order available in the stock");
        }
 for(Order ord:orderList){
 	if(ord.idO.equals(id)){
 		ordtest=ord;
 	}
 }
 if(ordtest==null)
 	System.out.println("Missing order in the list");
 else{
 	orderList.remove(ordtest);
 	System.out.println("Order Cancelled");
 }
 }
 public void displayProducts(){

        if (productList.isEmpty()) {
            System.out.println("No product available in the stock");
        } else {
            System.out.println("List of Product Available");
            productList.forEach(product ->product.getProduct());
        }
    }
 
public void displayOrders(){
	if(orderList.isEmpty()){
            System.out.println("No order saved.");	
	}
	else {
	System.out.println("List of order saved");
	orderList.forEach(order-> order.displayOrder());
	}
	
}
}
public  class Supermarket{
 	public static void main(String[]args){
 	//Definition of objects
 	 SupermarketStock Sp=new SupermarketStock();
 	 Supplier supplier;
        Customer customer;
        Order order;
        Product product;
        Scanner scanner = new Scanner(System.in);
        //Useful variables
        int choix,indic=0,count=1;
        String id;
        String date1,date2;
        String name,name1,address;
        String surname;
        String category;
        int price,c;
        int quantity;
        
        do{
            System.out.println("Welcome into the Stock Management System :)");
            System.out.println("Choose an unit : ");
            System.out.println("1.Product management\n2.Order management\n3.Quit");
            choix=scanner.nextInt();
            switch(choix){
                case 1:
                    System.out.println("1.Adding a Product\n2.Deleting a Product(irreversible action)\n3.Finding  a product by name\n4.Finding a product by category\n5.Finding a product by his supplier\n6.Display the list of product\n7.Restock a product\n8.Update a product(price)");
                    choix=scanner.nextInt();
                    switch(choix){
                        case 1:
                            System.out.println("Insert the date (DD-MM-YY) :");
                            scanner.nextLine();
                            date1=scanner.nextLine();	
                            System.out.println("Insert the name of the product : ");
                            name=scanner.nextLine();
                            System.out.println("Insert the price of the product : ");
                            price=scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Insert the  quantity of the product : ");
                            quantity=scanner.nextInt(); 
                            scanner.nextLine();
                            System.out.println("Insert the category of the product : ");
                            category=scanner.nextLine();  
                            System.out.println("Supplier informations: ");
                            System.out.println("Insert the supplier's name:");
                            name1=scanner.nextLine();
                            System.out.println("Surname : ");
                            surname=scanner.nextLine();
                            System.out.println("Address : ");
                            address=scanner.nextLine();
                           // product and supplier construction
                             supplier=new Supplier(name1,surname,address);
                             product=new Product(name,price,quantity,category,supplier);
                             Sp.createProduct(product);
                             product.getProduct();
                            System.out.println("On: "+date1); 
                             break;
                        case 2:
                        	System.out.println("Insert the id of the product : ");
                        	id=scanner.nextLine();
                        	Sp.deleteProduct(id);
                        	break; 
                        case 3:
                        	scanner.nextLine();
                        	System.out.println("Insert the name of the product : ");
                        	name=scanner.nextLine();
                        	Sp.searchProductByName(name);
                        	break;
                        case 4:
                        	scanner.nextLine();
                        	System.out.println("Insert the category : ");
                        	category=scanner.nextLine();
                        	Sp.searchProductByCategory(category);
                        	break;
                        case 5:
                        	scanner.nextLine();
                        	System.out.println("Insert the supplier id: ");
                        	name1=scanner.nextLine();
                        	Sp.searchProductBySupplier(name1);
                        	break;
                        case 6:
                        	 System.out.println("*********************List of  					products******************************");
                        	Sp.displayProducts();
                        	break; 
                        case 7:
                        	scanner.nextLine();
                        	System.out.println("Insert the product ID : ");
                        	id=scanner.nextLine();
                        	System.out.println("Insert the quantity to add : ");
                        	quantity=scanner.nextInt();
                        	Sp.restockProduct(id,quantity);
                        	break;
                        case 8:
                        	scanner.nextLine();
                        	System.out.println("Insert the product ID : ");
                        	id=scanner.nextLine();
                        	System.out.println("Insert the new price : ");
                        	price=scanner.nextInt();   
                        	Sp.updatePriceProduct(id,price);   
                        	break;                  	
                        default:
                        	break; 	                                                 
                     }
                    break;
                case 2:
                System.out.println("1.Creating an order\n2.Cancelling an order\n3.Display the list of order");
            	 choix=scanner.nextInt(); 
            	 scanner.nextLine();
		    	 switch(choix){
		    	 case 1:
		    	  Product[] prod=new Product[100];
		    	    int i=0;
                            System.out.println("Insert the date (DD-MM-YY):");
                            date1=scanner.nextLine();		    	    
                            System.out.println("Supplier informations: ");
                            System.out.println("Insert the supplier's name:");
                            name1=scanner.nextLine();
                            System.out.println("Surname : ");
                            surname=scanner.nextLine();
                            System.out.println("Address : ");
                            address=scanner.nextLine();
                            supplier=new Supplier(name1,surname,address);	
		    	     System.out.println("Customer informations");
		             System.out.println("Insert the customer's name:");
		             name1=scanner.nextLine();
		             System.out.println("Surname : ");
		             surname=scanner.nextLine();
		             System.out.println("Address : "); 
		             address=scanner.nextLine();
		             customer=new Customer(name1,surname,address);                            	    		    	   	     order=new Order(customer,supplier,date1);	    		     
		    	 	System.out.println("Ordering product");
		    	    do{
			    	System.out.println("Insert the name of the product : ");
		               name=scanner.nextLine();
		               System.out.println("Insert the price of the product : ");
		               price=scanner.nextInt();
		               scanner.nextLine();
		               System.out.println("Insert the  quantity of the product : ");
		               quantity=scanner.nextInt(); 
		               scanner.nextLine();
		               System.out.println("Insert the category of the product : ");
		               category=scanner.nextLine();
		               prod[i]=new Product(name,price,quantity,category,supplier);
				order.addProductOrder(prod[i]);
		    	 	i++;
		    	 	 System.out.println("Do you want to add another product? (0 = continue, 1 = stop)");
   				 indic = scanner.nextInt();
   				 scanner.nextLine();
		    	    }while(indic!=1);
		    	    //validation
		    	    Sp.createOrder(order);
		    	 break;
		    	 case 2:
		         System.out.println("Insert the id of the order");
                        id=scanner.nextLine();
                        Sp.cancelOrder(id);		       
		    	 break;
		    	 case 3:
		    	 System.out.println("********************List of orders*****************************");
		    	 Sp.displayOrders();
		    	 break;
		    	 
		    	 }               
                    break;
                case 3:
                   System.out.println("Aurevoir");
                    break;
                default:
                	System.out.println("Choix indisponible!");
                    break;
            }
        }while(choix!=3);
    }
}
 	
 		
 	
 	
 


