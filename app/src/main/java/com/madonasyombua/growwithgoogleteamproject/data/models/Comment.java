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
package com.madonasyombua.growwithgoogleteamproject.data.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Comment implements Parcelable {

    private int cid;
    private User user;
    private String text, commented, image;

    public Comment(int cid, User user, String text, String commented, String image) {
        this.cid = cid;
        this.user = user;
        this.text = text;
        this.commented = commented;
        this.image = image;
    }

    private Comment(Parcel in) {
        cid = in.readInt();
        text = in.readString();
        commented = in.readString();
        image = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public int getCid() {
        return cid;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getCommented() {
        return commented;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cid);
        dest.writeString(text);
        dest.writeString(commented);
        dest.writeString(image);
    }
}
