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
import com.google.firebase.database.Exclude
import com.madonasyombua.growwithgoogleteamproject.data.db.FirebaseObject
import java.util.*

/**
 * Created by madon on 3/20/2018.
 */
open class Post : FirebaseObject, Parcelable {
    var pid = 0
    var numberOfComments = 0
    var upvotes = 0
    var downvotes = 0
    var voted = 0
    var text: String? = null
    var posted: String? = null
    var image: String? = null
    var username: String? = null
    var comments: ArrayList<Comment?>? = null


    constructor(
        pid: Int, text: String?, username: String?, posted: String?, numberOfComments: Int,
        comments: ArrayList<Comment?>?, upvotes: Int, downvotes: Int, voted: Int, image: String?
    ) {
        this.pid = pid
        this.text = text
        this.posted = posted
        this.numberOfComments = numberOfComments
        this.comments = comments
        this.upvotes = upvotes
        this.downvotes = downvotes
        this.voted = voted
        this.image = image
        this.username = username
    }

    constructor(post: String?, name: String?, image: String?) {
        text = post
        username = name
        this.image = image
    }

    protected constructor(`in`: Parcel) {
        pid = `in`.readInt()
        numberOfComments = `in`.readInt()
        upvotes = `in`.readInt()
        downvotes = `in`.readInt()
        voted = `in`.readInt()
        text = `in`.readString()
        posted = `in`.readString()
        image = `in`.readString()
        username = `in`.readString()
        comments = `in`.createTypedArrayList(Comment.CREATOR)
    }

    @get:Exclude
    override val path: String
        get() = base + key

    @get:Exclude
    override val base: String
        get() = Paths.FEED + Paths.SL

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(pid)
        dest.writeInt(numberOfComments)
        dest.writeInt(upvotes)
        dest.writeInt(downvotes)
        dest.writeInt(voted)
        dest.writeString(text)
        dest.writeString(posted)
        dest.writeString(image)
        dest.writeString(username)
        dest.writeTypedList(comments)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Post?> = object : Parcelable.Creator<Post?> {
            override fun createFromParcel(`in`: Parcel): Post {
                return Post(`in`)
            }

            override fun newArray(size: Int): Array<Post?> {
                return arrayOfNulls(size)
            }
        }
    }
}