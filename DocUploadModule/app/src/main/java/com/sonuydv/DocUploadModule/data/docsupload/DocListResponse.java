package com.sonuydv.DocUploadModule.data.docsupload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DocListResponse implements Serializable {
    @SerializedName("resData")
    public List<DocumentData> docDataList;

    public List<DocumentData> getDocDataList() {
        return docDataList;
    }
}


