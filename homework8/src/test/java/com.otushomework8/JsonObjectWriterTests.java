package com.otushomework8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otus.homework8.JsonObject;
import com.otus.homework8.JsonObjectWriter;
import com.otushomework8.entity.Employee;
import com.otushomework8.entity.TestObject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonObjectWriterTests {

  @Test
  public void writeObjectToJsonTest() {
    Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    TestObject testObject = new TestObject();
    JsonObjectWriter objectWriter = new JsonObjectWriter();
    String jsonString = objectWriter.toJson(testObject);
    assertNotNull(jsonString);
    System.out.println(jsonString);
    TestObject fromJson = gson.fromJson(jsonString, TestObject.class);
    assertEquals(testObject, fromJson);
  }
}
