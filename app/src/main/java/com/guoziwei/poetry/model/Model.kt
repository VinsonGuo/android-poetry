package com.guoziwei.poetry.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by guoziwei on 2018/4/26 0026.
 */
data class BaseResponse<out T>(val status: Int, val msg: String, val data: T)

data class PageResponse<out T>(val data: T)

data class Poetry(val id: String, val title: String, val yunlv_rule: String, val author_id: String,
                  val content: String, val dynasty: String, val author: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(yunlv_rule)
        dest.writeString(author_id)
        dest.writeString(content)
        dest.writeString(dynasty)
        dest.writeString(author)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Poetry> {
        override fun createFromParcel(parcel: Parcel): Poetry {
            return Poetry(parcel)
        }

        override fun newArray(size: Int): Array<Poetry?> {
            return arrayOfNulls(size)
        }
    }
}