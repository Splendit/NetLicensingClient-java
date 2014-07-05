/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.labs64.netlicensing.provider;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import com.labs64.netlicensing.exception.RestException;
import com.labs64.netlicensing.provider.auth.Authentication;
import com.labs64.netlicensing.provider.auth.TokenAuthentication;
import com.labs64.netlicensing.provider.auth.UsernamePasswordAuthentication;

/**
 * Low level REST client implementation.
 * <p/>
 * This will also log each request in INFO level.
 */
public class RestProviderJersey extends AbstractRestProvider {

    private static final MediaType[] DEFAULT_ACCEPT_TYPES = { MediaType.APPLICATION_XML_TYPE };

    private static Client client;

    private String basePath;

    /**
     * @param basePath
     */
    public RestProviderJersey(String basePath) {
        this.basePath = basePath;
    }

    /*
     * @see com.labs64.netlicensing.provider.RestProvider#call(java.lang.String, java.lang.String, java.lang.Object, java.lang.Class, java.util.Map)
     */
    @Override
    public <REQ, RES> RestResponse<RES> call(final String httpMethod, final String urlTemplate, final REQ request, final Class<RES> responseType,
            final Map<String, Object> namedParams) throws RestException {
        try {
            final WebTarget target = getTarget(this.basePath);
            addAuthHeaders(target, getAuthentication());

            final Entity<REQ> requestEntity = Entity.entity(request, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
            final Response response;
            if (namedParams == null) {
                response = target.path(urlTemplate)
                        .request(DEFAULT_ACCEPT_TYPES)
                        .method(httpMethod, requestEntity);
            } else {
                response = target.path(urlTemplate)
                        .resolveTemplates(namedParams)
                        .request(DEFAULT_ACCEPT_TYPES)
                        .method(httpMethod, requestEntity);
            }

            final RestResponse<RES> restResponse = new RestResponse<RES>();
            restResponse.setStatusCode(response.getStatus());
            restResponse.setEntity(response.readEntity(responseType));
            return restResponse;
        } catch (RuntimeException e) {
            throw new RestException("Exception while calling service", e);
        }
    }

    /**
     * Get static instance of RESTful client
     *
     * @return RESTful client
     */
    private static Client getClient() {
        // initialize client only once since it's expensive operation
        if (client == null) {
            synchronized (RestProviderJersey.class) {
                if (client == null) {
                    client = ClientBuilder.newClient(new ClientConfig());
                    client.register(new LoggingFilter());
                }
            }
        }
        return client;
    }

    /**
     * Get the RESTful client target
     *
     * @param basePath
     * @return RESTful client target
     */
    private WebTarget getTarget(final String basePath) {
        final WebTarget target = getClient().target(basePath);
        return target;
    }

    /**
     * @param target
     * @param auth
     */
    private void addAuthHeaders(final WebTarget target, final Authentication auth) {
        if (auth != null) {
            if (auth instanceof UsernamePasswordAuthentication) {
                // see also https://jersey.java.net/documentation/latest/client.html#d0e4893
                HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(((UsernamePasswordAuthentication) auth).getUsername(),
                        ((UsernamePasswordAuthentication) auth).getPassword());
                target.register(feature);
            } else if (auth instanceof TokenAuthentication) {
                HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("apiKey", ((TokenAuthentication) auth).getToken());
                target.register(feature);
            }
        }
    }

}
