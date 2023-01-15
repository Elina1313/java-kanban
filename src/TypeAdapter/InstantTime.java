package TypeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.*;

import java.io.IOException;
import java.time.Instant;

public class InstantTime extends TypeAdapter<Instant> {
    @Override
    public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
        jsonWriter.value(instant.toEpochMilli());
    }

    @Override
    public Instant read(JsonReader jsonReader) throws IOException {
        return Instant.ofEpochMilli(Long.parseLong(jsonReader.nextString()));
    }
}
