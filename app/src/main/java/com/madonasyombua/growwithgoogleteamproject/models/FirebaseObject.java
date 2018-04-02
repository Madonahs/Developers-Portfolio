/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
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
