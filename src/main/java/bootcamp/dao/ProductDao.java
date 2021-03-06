package bootcamp.dao;

import java.math.BigDecimal;
import java.util.List;

import bootcamp.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bootcamp.model.products.Product;

@Component
public class ProductDao {
	
	private final String GET_PRODUCTS = "SELECT id, name, description, retail_price, wholesale_price FROM product";
    private final String GET_PRODUCT_BY_ID_SQL = GET_PRODUCTS + " where id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("#{'${listOfThings}'.split(',')}")
	private List<String> listOfThings;
	
	@Autowired
	private List<Product> items;
	
	public List<Product> getProducts() {
		return jdbcTemplate.query(GET_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
	}
	
	public Product getProductById(Integer id) {
		return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID_SQL, new Object[] {id}, new BeanPropertyRowMapper<>(Product.class));
	}

	public void updateProductWholesalePrice(Product product) {
		String sql = "update PRODUCT set wholesale_price = " + product.getWholesale_price() + "where id = " + product.getId();
		jdbcTemplate.update(sql);
	}

	public void updateProductRetailPrice(Product p, BigDecimal ourPrice){
		String sql = "update PRODUCT set retail_price = " + ourPrice + " where id = " + p.getId();
		jdbcTemplate.update(sql);
	}
	
	public List<String> getListOfThings(){
		return listOfThings;
	}
	
	public List<Product> getListOfProducts(){
		return items;
	}

}
