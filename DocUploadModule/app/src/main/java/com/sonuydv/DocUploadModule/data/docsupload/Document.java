package com.sonuydv.DocUploadModule.data.docsupload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Document implements Serializable {
   @SerializedName("docId")
  public String docId;
   @SerializedName("docName")
  public String docName;
   @SerializedName("minDocCount")
  public int minDocCount;
   @SerializedName("maxDocCount")
  public int maxDocCount;
   @SerializedName("isSkippable")
   public boolean isSkippable;
   @SerializedName("docImgBase64List")
  public List<String> docImgBase64List;
   @SerializedName("fieldList")
  public List<FieldList> fieldList;
}
