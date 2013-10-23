package org.kevinmrohr.android_blog.serialization;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
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
public class CustomDateDeserializer extends JsonDeserializer<DateTime> {

  private static DateTimeFormatter formatter = forPattern("MM/dd/yyyy");

  @Override
  public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationcontext) throws IOException {
    return formatter.parseDateTime(jsonParser.getText())
        .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
  }
}
