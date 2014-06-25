package com.labs64.netlicensing.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.labs64.netlicensing.domain.Constants;
import com.labs64.netlicensing.domain.entity.Product;
import com.labs64.netlicensing.domain.vo.Context;
import com.labs64.netlicensing.exception.RestException;
import com.labs64.netlicensing.schema.SchemaFunction;
import com.labs64.netlicensing.schema.context.Netlicensing;
import com.labs64.netlicensing.schema.context.ObjectFactory;
import com.labs64.netlicensing.schema.context.Property;
import com.labs64.netlicensing.util.JAXBUtils;

/**
 */
public class ProductServiceTest extends BaseServiceTest {

    private static final String PRODUCT_CUSTOM_PROPERTY = "CustomProperty";

    // *** NLIC test mock resource ***

    @Path(REST_API_PATH)
    public static class NLICResource {

        private ObjectFactory objectFactory = new ObjectFactory();

        @Path("hello")
        @GET
        public String getHello() {
            return "Hello NetLicensing!";
        }

        @Path("product")
        @POST
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public Response createProduct(MultivaluedMap<String, String> formParams) {

            final Netlicensing netlicensing = objectFactory.createNetlicensing();
            netlicensing.setItems(objectFactory.createNetlicensingItems());
            netlicensing.getItems().getItem().add(objectFactory.createItem());

            final List<Property> property = netlicensing.getItems().getItem().get(0).getProperty();
            for (String paramKey : formParams.keySet()) {
                final Property prop = objectFactory.createProperty();
                prop.setName(paramKey);
                prop.setValue(formParams.getFirst(paramKey));
                property.add(prop);
            }

            return Response.ok(netlicensing).build();
        }

        @Path("product/{productNumber}")
        @GET
        public Response getProduct(@PathParam("productNumber") String productNumber) {
            Netlicensing netlicensing = JAXBUtils.readObject(TEST_CASE_BASE + "netlicensing-product-get.xml", Netlicensing.class);

            SchemaFunction.propertyByName(netlicensing.getItems().getItem().iterator().next().getProperty(),
                    Constants.NUMBER).setValue(productNumber);

            return Response.ok(netlicensing).build();
        }

    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(NLICResource.class);
    }

    // *** NLIC Tests ***

    private static Context context;

    @BeforeClass
    public static void setup() {
        context = createContext();
    }

    @Test
    public void testHello() {
        final String hello = target(REST_API_PATH).path("hello").request().get(String.class);
        assertEquals("Hello NetLicensing!", hello);
    }

    @Test(expected = RestException.class)
    public void testNotExistingService() throws Exception {
        Context context = createContext();
        context.setBaseUrl("I_AM_NOT_EXISTING_SERVICE");
        ProductService.get(context, "dummyNumber");
    }

    @Test
    public void testCreate() throws Exception {
        final Product newProduct = new Product();
        newProduct.setNumber("P001-DEMO");
        newProduct.setActive(true);
        newProduct.setName("Demo Product");
        newProduct.setVersion("v3.2");
        newProduct.setLicenseeAutoCreate(true);
        newProduct.getProductProperties().put(PRODUCT_CUSTOM_PROPERTY, "CustomValue");

        final Product createdProduct = ProductService.create(context, newProduct);

        assertNotNull(createdProduct);
        assertEquals("P001-DEMO", createdProduct.getNumber());
        assertEquals(true, createdProduct.getActive());
        assertEquals("Demo Product", createdProduct.getName());
        assertEquals("v3.2", createdProduct.getVersion());
        assertEquals(true, createdProduct.getLicenseeAutoCreate());
        assertEquals("CustomValue", createdProduct.getProductProperties().get(PRODUCT_CUSTOM_PROPERTY));
    }

    @Test
    public void testGet() throws Exception {
        final String number = RandomStringUtils.randomAlphanumeric(8);

        Product res = ProductService.get(context, number);

        assertNotNull(res);
        assertEquals(number, res.getNumber());
    }

    @Ignore
    public void testList() throws Exception {

    }

    @Ignore
    public void testUpdate() throws Exception {

    }

    @Ignore
    public void testDelete() throws Exception {

    }

}
