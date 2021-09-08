package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";
    public final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
    private String apihost;

    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID uuid) {
        // BeerDto.class is for the template to do the mapping to BeerDto class
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid.toString(), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto){
        // postForLocation looks for a header called "location"
        return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
    }

    public void updateBeer(UUID uuid, BeerDto beerDto) {
        restTemplate.put(apihost + BEER_PATH_V1 + uuid.toString(), beerDto);
    }

    public void deleteBeer(UUID uuid) {
        restTemplate.delete(apihost + BEER_PATH_V1 + uuid);
    }

//    ----------------------- CustomerDto -----------------------

    public CustomerDto getCustomerById(UUID uuid) {
        return restTemplate.getForObject(getCustomerBasePath() + uuid, CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(getCustomerBasePath(), customerDto);
    }

    public void updateCustomer(UUID uuid, CustomerDto customerDto){
        restTemplate.put(getCustomerBasePath() + uuid, customerDto);
    }

    public void deleteCustomer(UUID uuid) {
        restTemplate.delete(getCustomerBasePath() + uuid);
    }

//    ----------------------- Lexmark -----------------------

    public ResponseEntity<String> getLexmarkType(String url1) {
        ResponseEntity<String> response = restTemplate.getForEntity(url1, String.class);
        return response;
    }


    public ResponseEntity<String> getLexmarkWarrantyInfo(String url2, String lexmarkType) {
        ResponseEntity<String> response = restTemplate.getForEntity(url2, String.class);
        return response;
    }


//    ----------------------- Helper Methods -----------------------

    public String getCustomerBasePath() {
        return apihost + CUSTOMER_PATH_V1;
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }

}
