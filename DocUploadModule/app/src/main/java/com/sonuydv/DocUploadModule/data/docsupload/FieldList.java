package com.sonuydv.DocUploadModule.data.docsupload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FieldList implements Serializable {
    @SerializedName("fieldName")
    public String fieldName;
    @SerializedName("fieldType")
    public String fieldType;
    @SerializedName("indent")
    public String indent;
    @SerializedName("isEditable")
    public boolean isEditable;
}
