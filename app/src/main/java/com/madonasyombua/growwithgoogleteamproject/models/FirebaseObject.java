package com.madonasyombua.growwithgoogleteamproject.models;

import com.google.firebase.database.Exclude;

@SuppressWarnings("unused")
public abstract class FirebaseObject {

  @Exclude
  String key;

  FirebaseObject(){}

  FirebaseObject(String key) {
    this.key = key;
  }

  @Exclude
  public String getKey(){return key;}

  @Exclude
  public void setKey(String key) {
    this.key = key;
  }

  @Exclude
  public boolean hasKey() {
    return key != null && !key.isEmpty();
  }

  @Exclude
  public abstract String getPath();

  @Exclude
  public abstract String getBase();

  @Override
  public boolean equals(Object o) {
    return this == o || !(o == null || getClass() != o.getClass()) && key
        .equals(((FirebaseObject) o).getKey());
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }
}
