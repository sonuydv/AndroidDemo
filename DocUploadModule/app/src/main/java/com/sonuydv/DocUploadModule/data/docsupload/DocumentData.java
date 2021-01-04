package com.sonuydv.DocUploadModule.data.docsupload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class DocumentData  implements Serializable {
    @SerializedName("docGroupId")
    public String docTypeId;
    @SerializedName("docGroupName")
    public String docTypeName;
    @SerializedName("docGroupLabel")
    public String docTypeLabel;
    @SerializedName("tbmand")
    public String tbmand;
    @SerializedName("idDocGroup")
    public String isDoc;
    @SerializedName("docList")
    public List<Document> docList;
}
