package cscie55.hw7.foodservice;

import cscie55.hw7.api.Shop;
import cscie55.hw7.api.Slicer;
import cscie55.hw7.impl.Address;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Collectors.*;

import cscie55.hw7.utils.NumUtil;
import cscie55.hw7.writer.MenuWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TakeOutShop implements Shop<Dish>, Slicer<List<Dish>, Integer, List<Dish>> {

    private List<Dish> menu = new ArrayList<>();
    String url = "https://cscie-55-out.s3.amazonaws.com/menu7.json";
    private static int ordersReceivedId = 0;
    private Map<Integer, ArrayDeque<FoodOrder>> ordersReadyOut = new HashMap<>();
    private Map<Integer, ArrayDeque<FoodOrder>> ordersIn = new HashMap<>();
    private List<Chef> chefs = new ArrayList<>();
    private List<DeliveryPerson> deliveryPeople = new ArrayList<>();
    private static final int NUMBER_CHEFS_ON_STAFF = 10;
    private static final int DELIVERY_PERSONS_ON_STAFF = 7;
    private String[] names = {"James Beard", "Melissa Clark", "Gordon Ramsey", "Ayesha Curry", "Anthony Bourdain", "Julia Child", "Gaida De Laurentis", "Jamie Oliver", "Jiro Ono", "Alice Waters"};

    /**
     * Constructor. Here we initialize the menu so we have something to sell
     */
    public TakeOutShop() {
        Dish[] menuArray = new Dish[0];
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader in = null;
        try {
            URL remoteDoc = new URL(url);
            in = new BufferedReader(
                    new InputStreamReader(remoteDoc.openStream()));
            
            menuArray = mapper.readValue(in, Dish[].class);
            in.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        // Dish[] menuArray = {
        //         new Dish("season fruit", true, 120, Dish.Type.OTHER),
        //         new Dish("pizza", true, 550, Dish.Type.OTHER),
        //         new Dish("prawns", false, 400, Dish.Type.FISH)
        // };

        /***** BELOW HERE THIS METHOD SHOULD REMAIN AS IS *******************/
        Arrays.stream(menuArray).forEach(menu::add);

        // create some chefs to run the shop
        int numChefs = NUMBER_CHEFS_ON_STAFF;
        while (numChefs > 0) {
            chefs.add(new Chef(names[numChefs - 1], ordersIn, ordersReadyOut));
            numChefs--;
        }

        int deliverors = DELIVERY_PERSONS_ON_STAFF;
        while (deliverors > 0) {
            deliveryPeople.add(new DeliveryPerson("Thomas", "Sims" + deliverors, new Address(2, 1, deliverors), this));
            deliverors--;
        }
    }

    @Override
    public List<Dish> getMenu() {
        return menu;
    }

    public List<Dish> getVegetarianMenu() {
        return menu.stream()
            .filter(p -> p.isVegetarian() == true)
            .collect(Collectors.toList());
    }

    public Map<Boolean, List<Dish>> partitionByCalorieLimit(int calorie) {
        return menu.stream()
            .collect(
                Collectors.partitioningBy(p -> p.getCalories() < calorie)
            );
    }


    /**
     * @return - List<Dish>
     */

    /**
     * @param dishes - List<Dish>
     * @param start
     * @param end
     * @return - List<Dish>
     */

    @Override
	public List<Dish> slice(List<Dish> dishes, Integer start, Integer end){
        return dishes.subList(start, end);
    }
	
	
	
	
    @Override
    public void setNewMenu(List<Dish> newMenu) {
        menu = newMenu;
    }

    /**
     * This method should use the MenuWriter's publish method to write a receipt for a foodOrder
     *
     * @param foodOrder
     */
    public void generateReceipt(FoodOrder foodOrder) {
        List<Dish> items = foodOrder.getItems();
        Address addr = foodOrder.getAddress();
        String fileName = addr.toString() + ".json";
        MenuWriter.publish(fileName, items);
    }

    /**
     * This method allows the menu to grow.
     *
     * @param dish
     */
    public void addMenuItem(Dish dish) {
        menu.add(dish);
    }

    /**
     * This method adds a list of dishes to the current menu
     *
     * @param dishes
     */
    public void addMenuItemList(List<Dish> dishes) {
        menu.addAll(dishes);
    }


    /**
     * This method receives the Dish[] from the world and uses that and the addresses to create a FoodOrder object.
     * It stores the order in a List in a Map, using the address' floorId as the Map's key
     * It calls processOrder to get the Chef working
     * and it returns a reference to the FoodOrder to the source.
     *
     * @param address
     * @param dishes
     * @return
     */
    @Override
    public FoodOrder placeOrder(Address address, Dish[] dishes) {
        FoodOrder order = new FoodOrder(address, dishes);
        order.setOrderAckId(++ordersReceivedId);
        int destinationFloor = address.getFloorId();
        ordersIn.computeIfAbsent(destinationFloor, ArrayDeque::new);
        ArrayDeque<FoodOrder> list = ordersIn.get(destinationFloor);
        list.addLast(order);
        return order;
    }
    
}

