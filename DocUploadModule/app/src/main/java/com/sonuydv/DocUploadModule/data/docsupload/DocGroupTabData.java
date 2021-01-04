package com.sonuydv.DocUploadModule.data.docsupload;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class DocGroupTabData {
    public final static int NOT_STARTED = 0;
    public final static int PENDING = 1;
    public final static int IN_PROGRESS = 2;
    public final static int COMPLETED = 3;
    public final static int SKIPPED = 4;


    public DocGroupTabData(){}

    public DocGroupTabData(String title, int status) {
        this.title = title;
        this.status = status;
    }

    @SerializedName("titl")
    private String title;

    @SerializedName("status")
    private int status = PENDING;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocGroupTabData)) return false;
        DocGroupTabData that = (DocGroupTabData) o;
        return /*getStatus() == that.getStatus() &&*/
                getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getStatus());
    }
}
