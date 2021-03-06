package org.md2k.cerebralcortex.cerebralcortexwebapi.models.stream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputStream {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("identifier")
    @Expose
    private String identifier;

    /**
     * No args constructor for use in serialization
     *
     */
    public InputStream() {
    }

    /**
     *
     * @param name
     * @param identifier
     */
    public InputStream(String name, String identifier) {
        super();
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}