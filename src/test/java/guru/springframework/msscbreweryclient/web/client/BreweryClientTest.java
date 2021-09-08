package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    private String lexmarkType;

    @Test
    void getBeerById() {
        BeerDto beerDto = client.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void testSaveNewBeer(){
        //given
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();

        URI uri = client.saveNewBeer(beerDto);

        assertNotNull(uri);

        System.out.println(uri.toString());
    }

    @Test
    void testUpdateBeer(){
        // given
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();

        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void testDeleteBeer(){
        client.deleteBeer(UUID.randomUUID());
    }

    //    ----------------------- CustomerDto Client Tests -----------------------

    @Test
    void testGetCustomerById(){
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void testSaveNewCustomer() {
        CustomerDto customerDto = CustomerDto.builder().name("Pedram").build();

        URI uri = client.saveNewCustomer(customerDto);

        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void testUpdateCustomer(){
        CustomerDto customerDto = CustomerDto.builder().name("Khoshdani").build();

        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void testDeleteCustomer(){
        client.deleteCustomer(UUID.randomUUID());
    }

//    @Test
    void testGetLexmarkType() {
        String url1 = "http://support.lexmark.com/index?page=warrantystatus&productCode=CS510&userlocale=EN_US";

        ResponseEntity<String> res = client.getLexmarkType(url1);

//        testGetLexmarkWarrantyInfo();
//        testGetLexmarkWarrantyInfo();
        lexmarkType = res.toString();

        assertNotNull(res);
    }

    @Test
    void testGetLexmarkWarrantyInfo(){
//      String url2 = "http://support.lexmark.com/index?frompage=warrantystatus&page=warrantystatusinfo&serialnum=5027159462012&modelno=5027&locale=dn&userlocale=EN_US";
        String url2 = "http://support.lexmark.com/index?frompage=warrantystatus&page=warrantystatusinfo&serialnum=502720946DV54&modelno=5027-630&locale=EN&userlocale=EN_US";

        testGetLexmarkType();

        ResponseEntity<String> res = client.getLexmarkWarrantyInfo(url2, lexmarkType);
//        ResponseEntity<String> res1 = client.getLexmarkWarrantyInfo(url2, lexmarkType);

        assertTrue(res.toString().contains("Start Date MM/DD/YYYY"));
    }
}
