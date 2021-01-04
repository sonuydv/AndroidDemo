package com.sonuydv.DocUploadModule.data.docsupload;

public class CurrentDoc {
    private int currentDocPosition;
    private int currentDocTypePosition;
    private String doc;

    public CurrentDoc(int currentDocPosition, int currentDocTypePosition, String doc) {
        this.currentDocPosition = currentDocPosition;
        this.currentDocTypePosition = currentDocTypePosition;
        this.doc = doc;
    }

    public int getCurrentDocPosition() {
        return currentDocPosition;
    }

    public void setCurrentDocPosition(int currentDocPosition) {
        this.currentDocPosition = currentDocPosition;
    }

    public int getCurrentDocTypePosition() {
        return currentDocTypePosition;
    }

    public void setCurrentDocTypePosition(int currentDocTypePosition) {
        this.currentDocTypePosition = currentDocTypePosition;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
