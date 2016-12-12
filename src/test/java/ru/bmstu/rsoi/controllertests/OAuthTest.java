package ru.bmstu.rsoi.controllertests;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.junit.Assert;
import org.junit.Test;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.entity.VersionedEntity;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

import static ru.bmstu.rsoi.controllertests.LibraryVisitorControllerTest.visitor1;
import static ru.bmstu.rsoi.controllertests.TestUtil.baseUrl;

/**
 * Created by ali on 10.12.16.
 */
public class OAuthTest {

    @Test(expected = AssertionError.class)
    public void requireOAuth() throws IOException {
        String addPath = "visitor/" + visitor1.getId();
        URI uri = UriBuilder.fromPath(baseUrl).build();
        Request getRq = Request.Get(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8");
        HttpResponse response = getRq.execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        Assert.assertTrue(writer.toString().contains("Full authentication"));
    }
}
