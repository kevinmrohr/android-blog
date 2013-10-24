package org.kevinmrohr.android_blog.module;

import dagger.Module;
import dagger.Provides;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;
import org.kevinmrohr.android_blog.fragments.BlogListFrag;
import org.kevinmrohr.android_blog.serialization.CustomDateDeserializer;
import org.kevinmrohr.android_blog.serialization.CustomDateSerializer;

@Module(injects = BlogListFrag.class)
public class ObjectMappingModule {
  @Provides ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null));
    module.addSerializer(DateTime.class, new CustomDateSerializer());
    module.addDeserializer(DateTime.class, new CustomDateDeserializer());
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(module);
    return mapper;
  }
}
