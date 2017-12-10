package com.chelonix.atmocalc.it;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import org.junit.*;
import org.junit.experimental.categories.*;
import org.junit.runner.*;

/**
 * Created with IntelliJ IDEA.
 * User: sirot
 * Date: 22/08/15
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
@RunWith(HttpJUnitRunner.class)
public class APIIntegrationTest
{
    @Rule
    public Destination destination = new Destination(this, "http://127.0.0.1:8181/");

    @Context
    private Response response;

    @HttpTest(method = Method.GET, path = "/atmosphere/?altitude=1000")
    public void check_response_is_sent() {
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @HttpTest(method = Method.GET, path = "/atmosphere/?altitude=azerty01")
    public void check_invalid_altitude() {
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getHeaders()).containsEntry("reason", Arrays.asList("For input string: \"azerty01\""));
    }

    @HttpTest(method = Method.GET, path = "/atmosphere/?altitude=-100")
    public void check_negative_altitude() {
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getHeaders()).containsEntry("reason", Arrays.asList("Altitude must be a positive integer"));
    }

    @HttpTest(method = Method.GET, path = "/atmosphere/?altitude=62000")
    public void check_high_altitude() {
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getHeaders()).containsEntry("reason", Arrays.asList("This model does not work for an altitude above 51 Km"));
    }

    @HttpTest(method = Method.GET, path = "/other")
    public void check_response_is_not_found() {
        assertThat(response.getStatus()).isEqualTo(404);
    }
}
