package org.kevinmrohr.android_blog.serialization;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import static org.joda.time.format.DateTimeFormat.forPattern;

/**
 * Created with IntelliJ IDEA.
 * User: krohr
 * Date: 10/15/13
 * Time: 3:47 PM
 */
public class CustomDateSerializer extends JsonSerializer<DateTime> {

  private static DateTimeFormatter formatter = forPattern("MM/dd/yyyy");

  @Override
  public void serialize(DateTime value, JsonGenerator gen,
                        SerializerProvider arg2)
      throws IOException, JsonProcessingException {

    gen.writeString(formatter.print(value));
  }
}
