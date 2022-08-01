package persistence;

import org.json.JSONObject;

// NOTE: borrows code from the JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
