package com.quickcommerce.thiskostha.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.CustomerAddressDTO;
import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.repository.AddressRepository;
import com.quickcommerce.thiskostha.repository.CartItemRepository;
import com.quickcommerce.thiskostha.repository.CustomerRepository;
import com.quickcommerce.thiskostha.repository.ItemRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private RestTemplate restTemplate;

    @Value("${myapp.api.key}")
    private String apiKey;


	public ResponseEntity<ResponseStructure<Customer>> register(CustomerDTO customerdto) {
		Customer customer = new Customer();
		customer.setName(customerdto.getName());
		customer.setPhone(customerdto.getPhone());
		customer.setEmail(customerdto.getEmail());
		customer.setGender(customerdto.getGender());

		customerRepo.save(customer);
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("customer saved successfully");
		rs.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomer(String phone) {
		Customer customer = customerRepo.findByPhone(phone);
		if (customer == null) {
			throw new RuntimeException();
		}
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setMessage("customer fteched successfully");
		rs.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(String phone) {
		customerRepo.deleteByPhone(phone);
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("customer deleted successfully");
		rs.setData(null);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<CartItem>> addtocart(String phone, Long itemId,int quantity) {

	    Customer customer = customerRepo.findByPhone(phone);
	            
	    Item item = itemRepository.findById(itemId)
	            .orElseThrow(() -> new RuntimeException("Item not found"));

	    CartItem cartItem = new CartItem();
	    cartItem.setItem(item);
	    cartItem.setQuantity(quantity);
	    

	    cartItemRepository.save(cartItem);
	    
	    if(item.getRestaurant().getId()!= customer.getCart().get(0).getItem().getRestaurant().getId()) {
	    	throw new RuntimeException("not from same restaurant");
	    }
	    
	    customer.getCart().add(cartItem);
	    customerRepo.save(customer);

	    ResponseStructure<CartItem> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Item added to cart");
	    response.setData(cartItem);

	    return ResponseEntity.ok(response);
	}

	public ResponseEntity<ResponseStructure<List<CartItem>>> getCart(String phone) {

	    Customer customer = customerRepo.findByPhone(phone);

	    if (customer == null) {
	        throw new RuntimeException("Customer not found");
	    }

	    List<CartItem> cartItems = customer.getCart();
	    ResponseStructure<List<CartItem>> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.OK.value());
	    response.setMessage("Cart fetched successfully");
	    response.setData(cartItems);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Address>> addAddress(String phone, CustomerAddressDTO address) {
		 Customer customer = customerRepo.findByPhone(phone);
		 String url="https://us1.locationiq.com/v1/reverse?key="+apiKey+"&lat="+ address.getCordinates().getLatitude()+"&lon="
					+address.getCordinates().getLongitude()+"&format=json&";
					
		 
		 Map<String,Object> response=restTemplate.getForObject(url,Map.class);
			
			Map<String,Object> addressMap=(Map<String,Object>) response.get("address");
			
			Address adr=new Address();
			adr.setLatitude(address.getCordinates().getLatitude());
			adr.setLongitude(address.getCordinates().getLongitude());

			adr.setArea((String) addressMap.get("suburb"));
			adr.setCity((String) addressMap.get("city"));
			adr.setDistrict((String) addressMap.get("county"));
			adr.setState((String) addressMap.get("state"));
			adr.setCountry((String) addressMap.get("country"));
			adr.setPincode((String) addressMap.get("postcode"));
			adr.setFlatNumber(address.getFlatNumber());
		    adr.setFloor(address.getFloor());
		    adr.setBuildingName(address.getBuildingName());
		    adr.setStreet(address.getStreet());
		    adr.setAddressType(address.getAddressType());
		    adr.setIsDefault(address.getIsDefault());
		    
		    if(customer.getAddresses()==null) {
				customer.setAddresses(Arrays.asList(adr));
			}else {
				customer.getAddresses().add(adr);
				}
		 customerRepo.save(customer);
		 addressRepository.save(adr);
		 ResponseStructure<Address> rs = new ResponseStructure<Address>();
			rs.setStatuscode(HttpStatus.CREATED.value());
			rs.setMessage("customer saved successfully");
			rs.setData(adr);

			return new ResponseEntity<ResponseStructure<Address>>(rs, HttpStatus.CREATED);
		 
		 
	}

}
