package com.example.moodup.data

import android.os.Parcel
import android.os.Parcelable

data class ComicDataWrapper(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: ComicDataContainer?,
    val etag: String?
)

data class ComicDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Comic>?
)

data class Comic(
    val id: Int?,
    val title: String?,
    val description: String?,
    val urls: List<MyUrl>?,
    val thumbnail: Image?,
    val creators: CreatorList?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(MyUrl),
        parcel.readParcelable(Image::class.java.classLoader),
        parcel.readParcelable(CreatorList::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeTypedList(urls)
        parcel.writeParcelable(thumbnail, flags)
        parcel.writeParcelable(creators, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comic> {
        override fun createFromParcel(parcel: Parcel): Comic {
            return Comic(parcel)
        }

        override fun newArray(size: Int): Array<Comic?> {
            return arrayOfNulls(size)
        }
    }
}

data class Image(
    val path: String?,
    val extension: String?
) : Parcelable {
    val url: String
        get() = "$path.$extension"

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeString(extension)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}


data class CreatorList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<CreatorSummary>?,
    val returned: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.createTypedArrayList(CreatorSummary),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(available)
        parcel.writeString(collectionURI)
        parcel.writeTypedList(items)
        parcel.writeValue(returned)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreatorList> {
        override fun createFromParcel(parcel: Parcel): CreatorList {
            return CreatorList(parcel)
        }

        override fun newArray(size: Int): Array<CreatorList?> {
            return arrayOfNulls(size)
        }
    }
}

data class CreatorSummary(
    val resourceURI: String?,
    val name: String?,
    val role: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreatorSummary> {
        override fun createFromParcel(parcel: Parcel): CreatorSummary {
            return CreatorSummary(parcel)
        }

        override fun newArray(size: Int): Array<CreatorSummary?> {
            return arrayOfNulls(size)
        }
    }
}

data class MyUrl(
    val type: String?,
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyUrl> {
        override fun createFromParcel(parcel: Parcel): MyUrl {
            return MyUrl(parcel)
        }

        override fun newArray(size: Int): Array<MyUrl?> {
            return arrayOfNulls(size)
        }
    }
}
