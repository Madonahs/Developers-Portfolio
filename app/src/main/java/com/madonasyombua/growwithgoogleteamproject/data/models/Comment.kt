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
package com.madonasyombua.growwithgoogleteamproject.data.models

import android.os.Parcel
import android.os.Parcelable

class Comment : Parcelable {
    private var cid: Int
    var user: User? = null
    var text: String?
    private var commented: String?
        private set
    var image: String?

    constructor(cid: Int, user: User?, text: String?, commented: String?, image: String?) {
        this.cid = cid
        this.user = user
        this.text = text
        this.commented = commented
        this.image = image
    }

    private constructor(`in`: Parcel) {
        cid = `in`.readInt()
        text = `in`.readString()
        commented = `in`.readString()
        image = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(cid)
        dest.writeString(text)
        dest.writeString(commented)
        dest.writeString(image)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Comment?> = object : Parcelable.Creator<Comment?> {
            override fun createFromParcel(`in`: Parcel): Comment? {
                return Comment(`in`)
            }

            override fun newArray(size: Int): Array<Comment?> {
                return arrayOfNulls(size)
            }
        }
    }
}